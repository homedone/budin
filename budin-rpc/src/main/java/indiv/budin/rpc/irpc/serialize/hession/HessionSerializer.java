package indiv.budin.rpc.irpc.serialize.hession;

import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;

public class HessionSerializer implements BaseSerializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        return null;
    }
}
