package indiv.budin.rpc.irpc.serialize.base;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.RpcResponse;
import indiv.budin.rpc.irpc.exception.SerializerException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JDKSerializer implements BaseSerializer{

    private final ThreadLocal<ObjectInputStream> inputLocal = new ThreadLocal<>();
    private final ThreadLocal<ObjectOutputStream > outputLocal = new ThreadLocal<>();
    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream out=new ByteArrayOutputStream()){
            ObjectOutputStream  output = outputLocal.get();
            if (output==null){
                output=new ObjectOutputStream (out);
                outputLocal.set(output);
            }
            output.writeObject(obj);
            return out.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            throw new SerializerException("serialize fail");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream in=new ByteArrayInputStream(bytes)){
            ObjectInputStream input = inputLocal.get();
            if (input==null){
                input=new ObjectInputStream (in);
                inputLocal.set(input);
            }
            Object obj = input.readObject();
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            throw new SerializerException("deserialize fail");
        }
    }
}
