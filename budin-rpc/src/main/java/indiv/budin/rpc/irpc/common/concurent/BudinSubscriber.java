package indiv.budin.rpc.irpc.common.concurent;

import indiv.budin.rpc.irpc.exception.MongoTimeoutException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2023/2/27 14 53
 * discription
 */
public class BudinSubscriber<T> implements Subscriber<T>, Future<List<T>> {
    private boolean success;
    transient private volatile Subscription subscription;
    private final List<T> receives;
    private final List<String> errors;

    transient private final byte[] lock;

    transient private CountDownLatch countDownLatch;

    private int count;

    public BudinSubscriber() {
        count = 1;
        lock = new byte[]{};
        errors = new ArrayList<>();
        receives = new ArrayList<>();
    }

    public BudinSubscriber(int n) {
        count = n;
        this.countDownLatch = new CountDownLatch(n);
        lock = new byte[]{};
        errors = new ArrayList<>();
        receives = new ArrayList<>();
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Integer.MAX_VALUE);
        subscription = s;
    }

    @Override
    public void onNext(T t) {
        System.out.println(t);
        receives.add(t);
    }

    @Override
    public void onError(Throwable t) {
        errors.add(t.getCause().toString());
    }

    @Override
    public void onComplete() {
        countDownLatch.countDown();
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
        if (countDownLatch.getCount()==0) return true;
        return false;
    }

    @Override
    public List<T> get() throws InterruptedException {
//        if (isDone()) return receives;
        countDownLatch.await();
        return receives;
    }

    @Override
    public List<T> get(long timeout, TimeUnit unit) throws InterruptedException {
        if (isDone()) return receives;
        if (countDownLatch.await(timeout, unit)) {
            return receives;
        } else {
            throw new MongoTimeoutException("Publisher onComplete timed out");
        }
    }

    public void reset() {
        countDownLatch = new CountDownLatch(count);
        success = false;
        receives.clear();
        errors.clear();
        subscription = null;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public List<T> getReceives() {
        return receives;
    }

    public List<String> getErrors() {
        return errors;
    }
}
