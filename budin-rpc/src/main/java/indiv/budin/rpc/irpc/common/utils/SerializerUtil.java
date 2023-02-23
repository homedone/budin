package indiv.budin.rpc.irpc.common.utils;

import indiv.budin.rpc.irpc.common.constants.SerializerType;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;
import indiv.budin.rpc.irpc.serialize.base.JDKSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoPoolSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoThreadSerializer;

public class SerializerUtil {
    public static boolean containsValue(byte b){
        SerializerType[] values = SerializerType.values();
        for (SerializerType st :
                values) {
            if (st.getType()==b) return true;
        }
        return false;
    }

    public static BaseSerializer getSerializer(byte b){
        if (SerializerType.JDK_SERIALIZER.getType()==b){
            return new JDKSerializer();
        }else if(SerializerType.KRYO_THREAD_SERIALIZER.getType()==b){
            return new KryoThreadSerializer();
        }else if(SerializerType.KRYO_POOL_SERIALIZER.getType()==b){
            return new KryoPoolSerializer();
        }else if (SerializerType.HESSIAN_SERIALIZER.getType()==b){
            return null;
        }else return null;
    }
}
