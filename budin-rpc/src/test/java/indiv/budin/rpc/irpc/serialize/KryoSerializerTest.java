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
        byte[] serialize = kryoThreadSerializer.serialize(response);
        logger.info(Arrays.toString(serialize));
        RpcResponse deserialize = (RpcResponse) kryoThreadSerializer.deserialize(serialize, RpcResponse.class);
        logger.info(deserialize.toString());
    }
    @Test
    public void kryoPoolSerializerTest(){
        KryoPoolSerializer kryoPoolSerializer = new KryoPoolSerializer();
        RpcRequest rpcRequest = new RpcRequest();
        RpcResponse response=RpcResponse.fail();
        byte[] serialize = kryoPoolSerializer.serialize(response);
        logger.info(Arrays.toString(serialize));
        RpcResponse deserialize = (RpcResponse) kryoPoolSerializer.deserialize(serialize, RpcResponse.class);
        logger.info(deserialize.toString());
    }

}
