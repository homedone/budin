package indiv.budin.rpc.irpc.common.constants;

import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;
import indiv.budin.rpc.irpc.serialize.base.JDKSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoPoolSerializer;
import indiv.budin.rpc.irpc.serialize.kryo.KryoThreadSerializer;

public enum SerializerType {

    JDK_SERIALIZER((byte) 1,"JDK"),
    KRYO_THREAD_SERIALIZER((byte) 2,"KRYO_THREAD"),
    KRYO_POOL_SERIALIZER((byte) 3,"KRYO_POOL"),
    HESSIAN_SERIALIZER((byte) 4,"HESSIAN");
    byte type;

    String description;

    SerializerType(byte type, String description) {
        this.type = type;
        this.description = description;
    }

    public byte getType() {
        return type;
    }
}
