package indiv.budin.rpc.irpc.server.handler;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.exception.RpcServiceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerHandler {

    private final ServiceCenter serviceCenter=(ServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class);

    public ServerHandler() {

    }

    public Object service(RpcRequest request){
        String methodName = request.getMethodName();
        String interfaceName = request.getInterfaceName();
        Object[] parameters = request.getParameters();
        Class<?>[] paramTypes = request.getParamTypes();
        String serviceName=request.getServiceNameWithNode();
        Object service = serviceCenter.getService(serviceName);
        try{
            Method method=service.getClass().getMethod(methodName,paramTypes);
            return method.invoke(service, parameters);
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception){
            throw new RpcServiceException("no such rpc service method");
        }
    }
}
