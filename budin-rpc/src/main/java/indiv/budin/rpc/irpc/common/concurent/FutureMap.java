package indiv.budin.rpc.irpc.common.concurent;

import indiv.budin.rpc.irpc.carrier.RpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author
 * @date 2023/2/22 21 07
 * discription
 */
public class FutureMap{
    private final Map<String, SyncFuture<Object>> responseMap;

    public FutureMap() {
        responseMap=new ConcurrentHashMap<>();
    }
    public SyncFuture<Object> get(String key){
        return responseMap.get(key);
    }
    public void put(String key,SyncFuture<Object> future){
        responseMap.put(key,future);
    }

}
