package indiv.budin.rpc.irpc.common.concurent;

import java.util.concurrent.Future;

/**
 * @author
 * @date 2023/2/22 15 21
 * discription
 */
public interface FuturePool<T> {
    ReuseFuture<T> obtain();
    void free(ReuseFuture<T> future);

    int size();

    int freeSize();

}
