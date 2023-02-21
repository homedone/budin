package indiv.budin.rpc.irpc.common.utils;

import java.util.concurrent.*;

/**
 * @author
 * @date 2023/2/21 20 03
 * discription
 */
public class SyncFuture<T> implements Future<T> {
    private CountDownLatch countDownLatch;
    private T response;

    public SyncFuture(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public SyncFuture(int count) {
        countDownLatch=new CountDownLatch(count);
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
        if (countDownLatch.await(timeout,unit)){
            return response;
        }
        return null;
    }

    public void setResponse(T response) {
        this.response = response;
        countDownLatch.countDown();
    }

    public T getResponse() {
        return response;
    }
}
