package indiv.budin.rpc.irpc.common.utils;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.MessageType;
import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;
import indiv.budin.rpc.irpc.serialize.base.JDKSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoPoolSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoThreadSerializer;

public class MessageUtil {
    public static Class<?> getMessageClass(byte b){
        if (MessageType.REQUEST.getType()==b){
            return RpcRequest.class;
        }else if(MessageType.REQUEST.getType()==b){
            return RpcResponse.class;
        }else return null;
    }

}
