package indiv.budin.rpc.irpc.serialize.base;

public class JDKSerializer implements BaseSerializer{
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        return null;
    }
}
