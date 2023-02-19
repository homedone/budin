package indiv.budin.rpc.irpc.center.base;

import indiv.budin.rpc.irpc.carrier.ServiceConfig;

public interface ServiceCenter {
     Object getService(String serviceName);

     void addService(ServiceConfig serviceConfig, Object service);
}
