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
public class SimpleNettyClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof RpcMessage){
            RpcMessage rpcMessage = (RpcMessage) o;
            if (rpcMessage.getMessageType() == MessageType.RESPONSE.getType()){
                RpcResponse response =(RpcResponse) rpcMessage.getData();
                channelHandlerContext.write(response);
            }

        }
    }
}
