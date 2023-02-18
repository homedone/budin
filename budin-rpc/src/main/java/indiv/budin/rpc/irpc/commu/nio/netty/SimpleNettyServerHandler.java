package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SimpleNettyServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof ByteBuf){
            ByteBuf buf = (ByteBuf) o;
            Decoder decoder=new Decoder();
            RpcMessage rpcMessage = (RpcMessage) decoder.decode(channelHandlerContext, buf);

        }
    }
}
