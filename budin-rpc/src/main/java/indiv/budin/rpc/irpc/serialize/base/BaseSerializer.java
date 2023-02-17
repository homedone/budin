package indiv.budin.rpc.irpc.serialize.base;

public interface BaseSerializer {
    public byte[] serialize(Object obj);
    public  Object deserialize(byte[] bytes,Class<?> clazz);
}
