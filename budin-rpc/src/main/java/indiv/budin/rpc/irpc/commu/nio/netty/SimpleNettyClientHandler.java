package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.concurent.FutureMap;
import indiv.budin.rpc.irpc.common.concurent.SyncFuture;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

/**
 * @author
 * @date 2023/2/20 21 37
 * discription
 */
public class SimpleNettyClientHandler extends SimpleChannelInboundHandler<RpcMessage> {

    /**
     * SimpleChannelInboundHandler 源码ChannelInboundHandle实现，泛型 I，并对资源做了释放
     *
     * @param channelHandlerContext
     * @param rpcMessage
     * @throws Exception
     */
    Logger logger= LoggerFactory.getLogger(SimpleNettyClientHandler.class);
    private final FutureMap futureMap;

    public SimpleNettyClientHandler() {
        futureMap = (FutureMap) FactoryUtil.getSingletonInstance(FutureMap.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        if (rpcMessage.getMessageType() == MessageType.RESPONSE.getType()) {
            RpcResponse response = (RpcResponse) rpcMessage.getData();
            logger.info(String.valueOf(response));
            channelHandlerContext.writeAndFlush(response);
            SyncFuture<Object> rpcResponseFuture = futureMap.get(response.getMessageId());
            if (response.isStatus()) {
                rpcResponseFuture.doneAndPut(response.getData());
            } else {
                rpcResponseFuture.done();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // write heartbeat to server
                logger.info("ping bounce");
                RpcMessage rpcMessage = new RpcMessage();
                rpcMessage.setMessageType(MessageType.BOUNCE.getType());
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);;
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

    }

}
