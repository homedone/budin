package indiv.budin.rpc.irpc.common.concurent;

import java.util.concurrent.Future;

public interface ReuseFuture<T> extends Future<T> {
    void reset();

    public void doneAndPut(T response);

    public void done() ;

    public T getResponse();
}
