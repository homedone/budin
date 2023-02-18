package indiv.budin.rpc.irpc.commu.nio.netty;

import indiv.budin.rpc.irpc.carrier.RpcMessage;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.utils.MessageUtil;
import indiv.budin.rpc.irpc.common.utils.SerializerUtil;
import indiv.budin.rpc.irpc.commu.nio.code.MessageCode;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class Decoder extends LengthFieldBasedFrameDecoder {
    private static final int M_FL = MessageCode.MAX_LENGTH;
    private static final int L_FO = MessageCode.MAGIC_LEN + MessageCode.VERSION_LEN;
    private static final int L_FL = MessageCode.FIELD_LENGTH;
    private static final int L_A = -(L_FO + L_FL);
    private static final int H_L = MessageCode.HEAD_LEN;

    public Decoder() {
        super(M_FL, L_FO, L_FL, L_A, 0);
    }

    /**
     * 先试试父解码，再解码
     *
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        if (decode instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) decode;
            int byteLen = byteBuf.readableBytes();
            if (byteLen >= H_L) {
                try {
                    return decode(byteBuf);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    byteBuf.release();
                }
            }
        }
        return decode;
    }

    protected Object decode(ByteBuf byteBuf) {
        byte[] bytes = new byte[MessageCode.MAGIC_LEN];
        byteBuf.readBytes(bytes);
        for (int i = 0; i < bytes.length; i++) {
            if (MessageCode.MAGIC[i] != bytes[i]) {
                throw new EncoderException("Magic error");
            }
        }
        byte b = byteBuf.readByte();
        if (MessageCode.VERSION != b) {
            throw new EncoderException("Version error");
        }
        int dataLength = byteBuf.readInt();
        byte messageType = byteBuf.readByte();
        byte serializerType = byteBuf.readByte();
        int messageId = byteBuf.readInt();
        RpcMessage rpcMessage = new RpcMessage();
        if (dataLength > 0) {
            byte[] data = new byte[dataLength];
            byteBuf.readBytes(data);
            BaseSerializer baseSerializer = SerializerUtil.getSerializer(serializerType);
            Class<?> messageClass = MessageUtil.getMessageClass(messageType);
            Object obj = baseSerializer.deserialize(data, messageClass);
            if (messageType == MessageType.REQUEST.getType()) {
                RpcRequest request = (RpcRequest) obj;
                rpcMessage.setData(request);
            } else if (messageType == MessageType.RESPONSE.getType()) {
                RpcResponse response = (RpcResponse) obj;
                rpcMessage.setData(response);
            }
        }
        rpcMessage.setMessageId(messageId);
        rpcMessage.setMessageType(messageType);
        rpcMessage.setSerializerType(serializerType);
        return rpcMessage;
    }
}
