package indiv.budin.rpc.irpc.commu;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.commu.nio.code.MessageCode;
import indiv.budin.rpc.irpc.commu.nio.netty.Decoder;
import indiv.budin.rpc.irpc.commu.nio.netty.Encoder;

public class NettyTest {

    void encoderAndDecoderTest(){
        int messageId=111;
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setMessageId(messageId);
        rpcMessage.setMessageType(MessageType.REQUEST.getType());
        rpcMessage.setSerializerType(SerializerType.KRYO_THREAD_SERIALIZER.getType());
        RpcRequest request = new RpcRequest();
        request.setMessageId("111");
        request.setMessageVersion("1.0");
        rpcMessage.setData(request);
        Encoder encoder=new Encoder();

        Decoder decoder=new Decoder();
    }
}
