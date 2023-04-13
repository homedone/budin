package indiv.budin.rpc.irpc.common.concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author
 * @date 2023/2/21 20 03
 * discription
 */
public class SyncFuture<T> implements ReuseFuture<T> {
    //CountDownLatch不能复用，改为CyclicBarrier
//    private CountDownLatch countDownLatch;

    private CyclicBarrier cyclicBarrier;
    private T response;

    private String futureName;

    public SyncFuture() {
//        countDownLatch = new CountDownLatch(1);
        cyclicBarrier = new CyclicBarrier(2);
    }

    public SyncFuture(String name) {
        futureName = name;
        new SyncFuture<>();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return response != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) {
        try {
            cyclicBarrier.await(timeout,unit);
            return response;
        } catch (BrokenBarrierException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reset() {
        cyclicBarrier.reset();
    }

    @Override
    public void done() {
        try {
            cyclicBarrier.await();
            reset();
        } catch (Exception e) {

        }
    }

    @Override
    public void doneAndPut(T response) {
        this.response = response;
        done();
    }


    public T getResponse() {
        return response;
    }

    public String getFutureName() {
        return futureName;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SyncFuture<String> stringSyncFuture = new SyncFuture<>();

        new Thread(() -> {
            System.out.println("已经完成");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stringSyncFuture.doneAndPut("完成了");
        }).start();

        System.out.println("等待");
        String s = stringSyncFuture.get();
        System.out.println(s);
    }
}
