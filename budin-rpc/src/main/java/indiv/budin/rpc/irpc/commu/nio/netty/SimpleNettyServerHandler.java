package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.server.handler.ServerHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleNettyServerHandler extends SimpleChannelInboundHandler<Object> {
    Logger logger = LoggerFactory.getLogger(SimpleNettyServerHandler.class);
    private final ServerHandler serverHandler = (ServerHandler) FactoryUtil.getSingletonInstance(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof RpcMessage){
            RpcMessage rpcMessage=(RpcMessage) obj;
            if (rpcMessage.getMessageType() != MessageType.BOUNCE.getType()) {
                RpcRequest request = (RpcRequest) rpcMessage.getData();
                Object result = serverHandler.service(request);
                RpcMessage responseMessage = new RpcMessage();
                responseMessage.setMessageId(rpcMessage.getMessageId());
                responseMessage.setMessageType(MessageType.RESPONSE.getType());
                responseMessage.setSerializerType(rpcMessage.getSerializerType());
                RpcResponse response=RpcResponse.success(request.getMessageId(),request.getMessageVersion(),result);
                responseMessage.setData(response);
                channelHandlerContext.writeAndFlush(responseMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
    }
}
