package indiv.budin.rpc.irpc.common.concurent;

import org.apache.tomcat.jni.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author
 * @date 2023/2/22 17 16
 * discription
 * MultiTaskFuture ,When you need to wait for several tasks to be completed and then start a task asynchronously,
 * it will wait for several result values asynchronously, otherwise it will wait all the time.
 */
public class MultiTaskFuture<T> implements Future<List<T>> {
    private CountDownLatch countDownLatch;

    private final byte[] lock;
    private final int taskCount;
    private List<T> results;

    public MultiTaskFuture(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        taskCount = (int) countDownLatch.getCount();
        lock = new byte[]{};
        results = new ArrayList<>();
    }

    public MultiTaskFuture(int count) {
        countDownLatch = new CountDownLatch(count);
        this.lock = new byte[]{};
        taskCount = (int) countDownLatch.getCount();
        results = new ArrayList<>();
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
        synchronized (lock) {
            return results.size() == taskCount;
        }
    }

    @Override
    public List<T> get() throws InterruptedException, ExecutionException {
        if (isDone()) return results;
        countDownLatch.await();
        return results;
    }

    @Override
    public List<T> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (isDone()) return results;
        if (countDownLatch.await(timeout, unit)) {
            return results;
        }
        reset();
        return null;
    }

    public void reset() {
        countDownLatch = new CountDownLatch(taskCount);
        results.clear();
    }

    public void done() {
        countDownLatch.countDown();
    }

    /**
     * 同步可以改为ReentrantLock
     * @param t
     */
    public void doneAndPut(T t) {
        if (isDone()) return;
        synchronized (lock) {
            results.add(t);
            done();
        }
    }

    public static void main(String[] args) {
        MultiTaskFuture<String> multiTaskFuture = new MultiTaskFuture<>(5);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Thread t=new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(2000)+100);
                    String s=Thread.currentThread().getName()+" have finished";
                    multiTaskFuture.doneAndPut(s);
                    System.out.println("-----------"+s+"-----------");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("make :  "+t.getName());
            t.start();
        }
        try{
            List<String> strings = multiTaskFuture.get();
            System.out.println(strings.toString());
            multiTaskFuture.reset();
            strings = multiTaskFuture.get();
            System.out.println(strings.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
