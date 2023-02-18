package indiv.budin.rpc.irpc.carrier;

import lombok.Data;

@Data
public class RpcMessage {
    private byte messageType;

    private byte serializerType;

    private int messageId;

    private Object data;
}
