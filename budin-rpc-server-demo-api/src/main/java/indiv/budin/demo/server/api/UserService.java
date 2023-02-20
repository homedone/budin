package indiv.budin.demo.server.api;


public interface UserService {
    User getUser(String userName);
    void addUser(User user);
    void removeUser(String userName);
    String getUserIntroduce(String userName);
}
