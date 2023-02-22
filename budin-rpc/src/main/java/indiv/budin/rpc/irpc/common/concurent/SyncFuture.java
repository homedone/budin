package indiv.budin.rpc.irpc.common.concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author
 * @date 2023/2/21 20 03
 * discription
 */
public class SyncFuture<T> implements Future<T>,ReuseFuture {
    private CountDownLatch countDownLatch;
    private T response;

    private String futureName;

    public SyncFuture() {
        countDownLatch=new CountDownLatch(1);
    }

    public SyncFuture(String name) {
        futureName=name;
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
        return response!=null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        countDownLatch.wait();
        return response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (countDownLatch.await(timeout, unit)) {
            return response;
        }
        return null;
    }
    @Override
    public void reset() {
        countDownLatch=new CountDownLatch(1);
    }

    public void done() {
        countDownLatch.countDown();
    }

    public void doneAndPut(T response) {
        this.response=response;
        done();
    }


    public T getResponse() {
        return response;
    }

    public String getFutureName() {
        return futureName;
    }
}
