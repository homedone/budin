package indiv.budin.rpc.irpc.common.concurent;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author
 * @date 2023/2/22 15 25
 * discription
 */
public class SyncFuturePool<T> implements FuturePool<T> {

    private final AtomicInteger count;
    private final AtomicInteger queueSize;

    private final int cap;

    private final Queue<Future<T>> blockingQueue;

    private final ReentrantLock reentrantLock;

    public SyncFuturePool(int capacity) {
        cap = capacity;
        blockingQueue = new LinkedList<>();
        count = new AtomicInteger(0);
        queueSize = new AtomicInteger(0);
        reentrantLock=new ReentrantLock();
    }

    @Override
    public Future<T> obtain() {
        while (count.get()==cap && queueSize.get()==0){}
        reentrantLock.lock();
        if (count.get()<cap){
            blockingQueue.offer(new SyncFuture<>());
            queueSize.incrementAndGet();
            count.incrementAndGet();
        }
        Future<T> poll = blockingQueue.poll();
        queueSize.decrementAndGet();
//        System.out.println( " borrow "+" ， rest count is "+freeSize()+"， all count is "+size());
        reentrantLock.unlock();
        return poll;
    }

    /**
     * free
     * @param future
     */
    @Override
    public void free(Future<T> future) {
        blockingQueue.offer(future);
        int i = queueSize.incrementAndGet();
//        System.out.println( " give back "+" ，rest count is "+i+"， all count is "+size());
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public int freeSize() {
        return queueSize.get();
    }

    public static void main(String[] args) throws InterruptedException {
        SyncFuturePool<String> stringSyncFuturePool = new SyncFuturePool<>(4);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                try {
                    Future<String> future = stringSyncFuturePool.obtain();
                    String s = Thread.currentThread().getName();
                    stringSyncFuturePool.free(future);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("make :  " + t.getName());
            t.start();
        }
        Thread.sleep(1000);
        System.out.println("rest count is "+stringSyncFuturePool.freeSize()+"， all count is "+stringSyncFuturePool.size());
    }

}
