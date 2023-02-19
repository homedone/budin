package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.server.handler.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleNettyServerHandler extends SimpleChannelInboundHandler {

    private ServerHandler serverHandler= (ServerHandler) FactoryUtil.getSingletonInstance(ServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof RpcMessage){
            RpcMessage rpcMessage = (RpcMessage) o;
            if (rpcMessage.getMessageType()!= MessageType.BOUNCE.getType()){

                RpcRequest request =(RpcRequest) rpcMessage.getData();
                Object result = serverHandler.service(request);
                RpcMessage responseMessage = new RpcMessage();
                responseMessage.setMessageId(rpcMessage.getMessageId());
                responseMessage.setMessageType(MessageType.RESPONSE.getType());
                responseMessage.setSerializerType(rpcMessage.getSerializerType());
                responseMessage.setData(request);
                channelHandlerContext.write(responseMessage);
            }

        }
    }
}
