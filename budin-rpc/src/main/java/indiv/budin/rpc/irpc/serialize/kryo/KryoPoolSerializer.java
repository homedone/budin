package indiv.budin.rpc.irpc.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInputStream;
import com.esotericsoftware.kryo.io.ByteBufferOutputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.common.constants.ExceptionMessage;
import indiv.budin.rpc.irpc.exception.SerializerException;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;

import java.io.ByteArrayOutputStream;

/**
 * Pool Serializer thread safe
 */
public class KryoPoolSerializer implements BaseSerializer {
    private static Pool<Kryo> kryoPool;

    private static Pool<Input> inputPool;

    private static Pool<Output> outputPool;

    public KryoPoolSerializer() {
        new KryoPoolSerializer(8, 16, 16);
    }

    public KryoPoolSerializer(Integer kryoPoolSize, Integer inputPoolSize, Integer outPutSize) {
        kryoPool = new Pool<Kryo>(true, false, kryoPoolSize) {
            @Override
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setRegistrationRequired(true);
                kryo.setReferences(false);
                kryo.register(RpcRequest.class);
                kryo.register(RpcResponse.class);
                return kryo;
            }
        };
        inputPool = new Pool<Input>(true, false, inputPoolSize) {
            @Override
            protected Input create() {
                return new Input(new ByteBufferInputStream());
            }
        };
        outputPool = new Pool<Output>(true, false, outPutSize) {
            @Override
            protected Output create() {
                return new Output(new ByteArrayOutputStream());
            }
        };
    }

    @Override
    public byte[] serialize(Object obj) {
        try{
            Output output = outputPool.obtain();
            Kryo kryo = kryoPool.obtain();
            output.flush();
            kryo.writeObject(output,obj);
            return output.toBytes();
        } catch (Exception e) {
            String message=this.getClass().getName()+ ExceptionMessage.SERIALIZE_EXCEPTION;
            e.printStackTrace();
            throw new SerializerException(message);
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try{
            Input input = inputPool.obtain();
            Kryo kryo = kryoPool.obtain();
            input.setBuffer(bytes);
            Object obj = kryo.readObject(input, clazz);
            return obj;
        } catch (Exception e) {
            String message=this.getClass().getName()+ ExceptionMessage.DESERIALIZE_EXCEPTION;
            e.printStackTrace();
            throw new SerializerException(message);
        }
    }
}
