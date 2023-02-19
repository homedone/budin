package indiv.budin.rpc.irpc.carrier;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * rpc请求应该包括哪些参数？
 */

@Data
@NoArgsConstructor
@ToString
public class RpcRequest implements Serializable {
    private String messageId;
    private String methodName;
    private String interfaceName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String messageVersion;
    private String serviceName;
    private String nodeName;
    public String getServiceNameWithNode(){
        return getServiceName()+"."+getNodeName();
    }

}
