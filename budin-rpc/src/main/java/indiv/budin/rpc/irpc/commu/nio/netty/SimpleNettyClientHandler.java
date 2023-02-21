package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        if (rpcMessage.getMessageType() == MessageType.RESPONSE.getType()) {
            RpcResponse response = (RpcResponse) rpcMessage.getData();
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
