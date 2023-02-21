package indiv.budin.rpc.irpc.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInputStream;
import com.esotericsoftware.kryo.io.ByteBufferOutputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.ExceptionMessage;
import indiv.budin.rpc.irpc.exception.SerializerException;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;

/**
 * ThreadLocal Serializer
 */
public class KryoThreadSerializer implements BaseSerializer {

    public KryoThreadSerializer(){

    }
    private final ThreadLocal<Kryo> threadLocal=new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo=new Kryo();
            kryo.setRegistrationRequired(false);
            kryo.setReferences(true);
            kryo.register(RpcRequest.class);
            kryo.register(RpcResponse.class);
            kryo.register(java.lang.Class[].class);
            return kryo;
        }
    };
    private final ThreadLocal<Input> inputLocal = new ThreadLocal<>();

    /**
     * output.getBuffer() and output.toBytes() return different length
     * @param obj
     * @return byte[]
     */
    @Override
    public byte[] serialize(Object obj) {
        try {
            Kryo kryo=threadLocal.get();
            ByteBufferOutputStream byteBufferOutputStream = new ByteBufferOutputStream();
            Output output=new Output(byteBufferOutputStream);
            kryo.writeObject(output,obj);
            byte[] bytes=output.toBytes();
            byteBufferOutputStream.flush();
            byteBufferOutputStream.close();
            threadLocal.remove();
            return bytes;
        }catch (Exception e){
            String message=this.getClass().getName()+ ExceptionMessage.SERIALIZE_EXCEPTION;
            e.printStackTrace();
            throw new SerializerException(message);
        }
    }

    /**
     * deserialize byte[]
     * @param bytes
     * @param clazz
     * @return Object
     */
    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Kryo kryo=threadLocal.get();
            Input input=inputLocal.get();
            if (input==null){
                input=new Input(new ByteBufferInputStream());
                inputLocal.set(input);
            }
            input.setBuffer(bytes);
            Object obj = kryo.readObject(input, clazz);
            input.close();
            threadLocal.remove();
            inputLocal.remove();
            return obj;
        }catch (Exception e){
            String message=this.getClass().getName()+ ExceptionMessage.DESERIALIZE_EXCEPTION;
            e.printStackTrace();
            throw new SerializerException(message);
        }
    }
}
