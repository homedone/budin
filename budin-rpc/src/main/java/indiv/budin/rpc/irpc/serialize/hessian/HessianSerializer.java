package indiv.budin.rpc.irpc.serialize.hessian;

import com.caucho.hessian.io.*;
import indiv.budin.rpc.irpc.serialize.base.BaseSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements BaseSerializer {
    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        AbstractHessianOutput out = new Hessian2Output(os);

        out.setSerializerFactory(new SerializerFactory());
        try {
            out.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                out.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return os.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        AbstractHessianInput in = new Hessian2Input(is);
        in.setSerializerFactory(new SerializerFactory());
        Object value;
        try {
            value = in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
