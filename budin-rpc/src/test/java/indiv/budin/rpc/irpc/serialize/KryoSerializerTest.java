package indiv.budin.rpc.irpc.serialize;

import indiv.budin.rpc.irpc.carrier.ResponseResult;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.serialize.kryo.KryoPoolSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoThreadSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class KryoSerializerTest {
    Logger logger=LoggerFactory.getLogger(KryoSerializerTest.class);

    @Test
    public void kryoThreadSerializerTest(){
        KryoThreadSerializer kryoThreadSerializer = new KryoThreadSerializer();
        RpcRequest rpcRequest = new RpcRequest();
        RpcResponse response=RpcResponse.fail();
        byte[] serialize = kryoThreadSerializer.serialize(rpcRequest);
        logger.info(Arrays.toString(serialize));
        RpcRequest deserialize = (RpcRequest) kryoThreadSerializer.deserialize(serialize, RpcResponse.class);
        logger.info(deserialize.toString());
    }
    @Test
    public void kryoPoolSerializerTest(){
        KryoPoolSerializer kryoPoolSerializer = new KryoPoolSerializer();
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setNodeName("aaa");
        RpcResponse response=RpcResponse.fail();
        byte[] serialize = kryoPoolSerializer.serialize(rpcRequest);
        logger.info(Arrays.toString(serialize));
        RpcRequest deserialize = (RpcRequest) kryoPoolSerializer.deserialize(serialize, RpcRequest.class);
        logger.info(deserialize.toString());
    }

}
