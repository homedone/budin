package indiv.budin.rpc.irpc.common.concurent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author
 * @date 2023/4/13 14 02
 * discription
 */
public class BlockingFuturePool<T> implements FuturePool<T> {

    private final AtomicInteger count;
    private final Semaphore semphore;

    private final BlockingQueue<ReuseFuture<T>> blockingQueue;

    private int cap;

    public BlockingFuturePool(int cap) {
        this.cap = cap;
        semphore = new Semaphore(cap);
        blockingQueue = new ArrayBlockingQueue<>(cap);
        count = new AtomicInteger(0);
    }

    @Override
    public ReuseFuture<T> obtain() {
        try {
            semphore.acquire();
            if (count.get() < cap) {
                count.incrementAndGet();
                ReuseFuture future = new SyncFuture<>();
                blockingQueue.offer(future);
            }
        } catch (Exception e) {
        }
//        System.out.println("有 "+freeSize()+" 个, 借 1 个");
        return blockingQueue.poll();
    }

    @Override
    public void free(ReuseFuture<T> future) {
        blockingQueue.offer(future);
//        System.out.println("还 1 个，还剩 "+freeSize());
        semphore.release();
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public int freeSize() {
        return blockingQueue.size();
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingFuturePool<String> stringSyncFuturePool = new BlockingFuturePool<>(4);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                try {
                    ReuseFuture<String> future = stringSyncFuturePool.obtain();
                    System.out.println(future.hashCode());
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
        System.out.println("rest count is " + stringSyncFuturePool.freeSize() + "， all count is " + stringSyncFuturePool.size());
    }

}
