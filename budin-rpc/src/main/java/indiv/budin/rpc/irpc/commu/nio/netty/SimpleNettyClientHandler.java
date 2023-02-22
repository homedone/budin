package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.concurent.FutureMap;
import indiv.budin.rpc.irpc.common.concurent.SyncFuture;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Future;

/**
 * @author
 * @date 2023/2/20 21 37
 * discription
 */
public class SimpleNettyClientHandler extends SimpleChannelInboundHandler<RpcMessage> {

    /**
     * SimpleChannelInboundHandler 源码ChannelInboundHandle实现，泛型 I，并对资源做了释放
     * @param channelHandlerContext
     * @param rpcMessage
     * @throws Exception
     */
    private final FutureMap futureMap;

    public SimpleNettyClientHandler() {
        futureMap= (FutureMap) FactoryUtil.getSingletonInstance(FutureMap.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        if (rpcMessage.getMessageType() == MessageType.RESPONSE.getType()) {
            RpcResponse response = (RpcResponse) rpcMessage.getData();
            System.out.println(response.toString());
            channelHandlerContext.writeAndFlush(response);
            SyncFuture<Object> rpcResponseFuture = futureMap.get(response.getMessageId());
            if (response.isStatus()) {
                rpcResponseFuture.doneAndPut(response.getData());
            }else {
                rpcResponseFuture.done();
            }
        }
    }
}
