package indiv.budin.rpc.irpc.common.concurent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author
 * @date 2023/2/27 19 39
 * discription
 */
public class SubscriberPool<T> {

    private final AtomicInteger count;
    private final AtomicInteger queueSize;

    private final int cap;

    private final Queue<BudinSubscriber<T>> blockingQueue;

    private final ReentrantLock reentrantLock;
    public SubscriberPool(int capacity) {
        cap = capacity;
        blockingQueue = new LinkedList<>();
        count = new AtomicInteger(0);
        queueSize = new AtomicInteger(0);
        reentrantLock=new ReentrantLock();
    }
    BudinSubscriber<T> obtain(){
        while (count.get()==cap && queueSize.get()==0){}
        reentrantLock.lock();
        if (count.get()<cap){
            blockingQueue.offer(new BudinSubscriber<>());
            queueSize.incrementAndGet();
            count.incrementAndGet();
        }
        BudinSubscriber<T> poll = blockingQueue.poll();
        queueSize.decrementAndGet();
//        System.out.println( " borrow "+" ， rest count is "+freeSize()+"， all count is "+size());
        reentrantLock.unlock();
        return poll;
    }
    public void free(BudinSubscriber<T> subscriber) {
        blockingQueue.offer(subscriber);
        int i = queueSize.incrementAndGet();
//        System.out.println( " give back "+" ，rest count is "+i+"， all count is "+size());
    }

    public int size() {
        return count.get();
    }

    public int freeSize() {
        return queueSize.get();
    }
}
