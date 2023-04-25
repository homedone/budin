package indiv.budin.rpc.irpc.common.utils;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.MessageType;


public class MessageUtil {
    public static Class<?> getMessageClass(byte b){
        if (MessageType.REQUEST.getType()==b){
            return RpcRequest.class;
        }else if(MessageType.RESPONSE.getType()==b){
            return RpcResponse.class;
        }else if (MessageType.BOUNCE.getType()==b){
            return RpcRequest.class;
        }else{
            return null;
        }
    }

}
