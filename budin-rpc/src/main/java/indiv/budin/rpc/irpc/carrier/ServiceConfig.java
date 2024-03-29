package indiv.budin.rpc.irpc.carrier;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class ServiceConfig {
    private String host;
    private String serviceName;
    private int port;
    private String nodeName;
    private String version;
    public String getServiceNameWithNode(){
        return getServiceName()+"."+getNodeName();
    }
}
