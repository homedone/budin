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
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleNettyServerHandler extends SimpleChannelInboundHandler<Object> {
    Logger logger = LoggerFactory.getLogger(SimpleNettyServerHandler.class);
    private final ServerHandler serverHandler = (ServerHandler) FactoryUtil.getSingletonInstance(ServerHandler.class);

   private AtomicInteger count;
   private final int trigger=3;

    public SimpleNettyServerHandler() {
        count=new AtomicInteger(0);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof RpcMessage){
            RpcMessage rpcMessage=(RpcMessage) obj;
            RpcMessage responseMessage = new RpcMessage();
            if (rpcMessage.getMessageType() != MessageType.BOUNCE.getType()) {
                RpcRequest request = (RpcRequest) rpcMessage.getData();
                logger.info("requestId: "+request.getMessageId());
                Object result = serverHandler.service(request);
                responseMessage.setMessageId(rpcMessage.getMessageId());
                responseMessage.setMessageType(MessageType.RESPONSE.getType());
                responseMessage.setSerializerType(rpcMessage.getSerializerType());
                if (channelHandlerContext.channel().isActive()){
                    RpcResponse response=RpcResponse.success(request.getMessageId(),request.getMessageVersion(),result);
                    responseMessage.setData(response);
                }else {
                    RpcResponse response=RpcResponse.fail();
                    responseMessage.setData(response);
                }
            }else {
                logger.info("bounce");
                responseMessage.setMessageType(MessageType.BOUNCE.getType());
            }
            channelHandlerContext.writeAndFlush(responseMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            int c = count.incrementAndGet();
            if (c==trigger) {
                logger.info("goodbye");
                ctx.disconnect();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


}
