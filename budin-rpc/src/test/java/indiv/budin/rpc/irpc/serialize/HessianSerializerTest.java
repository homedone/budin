package indiv.budin.rpc.irpc.serialize;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.serialize.base.JDKSerializer;
import indiv.budin.rpc.irpc.serialize.hessian.HessianSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoPoolSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoThreadSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author
 * @date 2023/2/23 20 56
 * discription
 */
public class HessianSerializerTest {
    Logger logger= LoggerFactory.getLogger(KryoSerializerTest.class);

    @Test
    public void HessianSerializerTest(){
        HessianSerializer hessianSerializer = new HessianSerializer();
        RpcRequest rpcRequest = new RpcRequest();
        RpcResponse response=RpcResponse.fail();
        byte[] serialize = hessianSerializer.serialize(response);
        logger.info(Arrays.toString(serialize));
        RpcResponse deserialize = (RpcResponse) hessianSerializer.deserialize(serialize, RpcResponse.class);
        logger.info(deserialize.toString());
    }
    @Test
    public void JDKSerializerTest(){
        JDKSerializer jdkSerializer = new JDKSerializer();
        RpcRequest rpcRequest = new RpcRequest();
        RpcResponse response=RpcResponse.fail();
        byte[] serialize = jdkSerializer.serialize(response);
        logger.info(Arrays.toString(serialize));
        RpcResponse deserialize = (RpcResponse) jdkSerializer.deserialize(serialize, RpcResponse.class);
        logger.info(deserialize.toString());
    }
}
