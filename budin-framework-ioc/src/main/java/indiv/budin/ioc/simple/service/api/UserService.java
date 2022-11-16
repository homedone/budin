package indiv.budin.ioc.simple.service.api;

import indiv.budin.ioc.annotations.IocService;
import indiv.budin.ioc.simple.entity.User;


public interface UserService {
    User getUser(String userName);
    void addUser(User user);
    void removeUser(String userName);
}
