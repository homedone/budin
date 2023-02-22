package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.common.utils.CompressionUtil;
import indiv.budin.rpc.irpc.common.utils.SerializerUtil;
import indiv.budin.rpc.irpc.commu.nio.code.MessageCode;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;


public class Encoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof RpcMessage) {
            RpcMessage rpcMessage = (RpcMessage) o;
            byteBuf.writeBytes(MessageCode.MAGIC);
            byteBuf.writeBytes(new byte[]{MessageCode.VERSION});
            byteBuf.writerIndex(byteBuf.writerIndex() + MessageCode.FIELD_LENGTH);
            byteBuf.writeBytes(new byte[]{rpcMessage.getMessageType()});
            byteBuf.writeBytes(new byte[]{rpcMessage.getSerializerType()});
            byteBuf.writeInt(rpcMessage.getMessageId());
            int bodyLen=0;
            if (rpcMessage.getMessageType() == MessageType.REQUEST.getType()
                    || rpcMessage.getMessageType() == MessageType.RESPONSE.getType()) {
                BaseSerializer serializer = SerializerUtil.getSerializer(rpcMessage.getSerializerType());
                if (serializer == null) {
                    throw new EncoderException("Serializer not be find");
                }
                byte[] serialize = serializer.serialize(rpcMessage.getData());
                byte[] compress = CompressionUtil.compress(serialize);
                byteBuf.writeBytes(compress);
                bodyLen=compress.length;
            }
            int pos = byteBuf.writerIndex();
            byteBuf.writerIndex(MessageCode.MAGIC_LEN+MessageCode.VERSION_LEN);
            byteBuf.writeInt(MessageCode.HEAD_LEN+bodyLen);
            byteBuf.writerIndex(pos);
        }
    }
}
