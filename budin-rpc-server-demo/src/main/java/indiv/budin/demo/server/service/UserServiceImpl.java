package indiv.budin.demo.server.service;

import indiv.budin.demo.server.api.User;
import indiv.budin.ioc.annotations.IocService;
import indiv.budin.demo.server.api.UserService;
import indiv.budin.rpc.irpc.annotation.RpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2022/11/16 19 08
 * discription
 */
@RpcService(serviceName = "indiv.budin.demo.server.service.UserService",version = "1.0",node = "1.0")
@IocService
public class UserServiceImpl implements UserService {
    private static final Map<String, User> users=new ConcurrentHashMap<>();
    static {
        User user=new User();
        user.setUserName("dxq");
        user.setAddress(" Beijing");
        users.put("dxq",user);
    }
    @Override
    public User getUser(String userName) {
        if (!users.containsKey(userName)) return null;
        return users.get(userName);
    }

    @Override
    public void addUser(User user) {
        System.out.println(user.getUserName());
        users.put(user.getUserName(),user);
    }

    @Override
    public void removeUser(String userName) {
        if (!users.containsKey(userName)) return;
        users.remove(userName);
    }
    @Override
    public String getUserIntroduce(String userName) {
        if (!users.containsKey(userName)) return null;
        return users.get(userName).introduce();
    }
}
