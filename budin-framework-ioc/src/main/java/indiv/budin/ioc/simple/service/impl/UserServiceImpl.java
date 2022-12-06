package indiv.budin.ioc.simple.service.impl;

import indiv.budin.ioc.annotations.IocService;
import indiv.budin.ioc.simple.entity.User;
import indiv.budin.ioc.simple.service.api.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2022/11/16 19 08
 * discription
 */
@IocService
public class UserServiceImpl implements UserService {
    private static final Map<String, User> users=new ConcurrentHashMap<>();
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
}
