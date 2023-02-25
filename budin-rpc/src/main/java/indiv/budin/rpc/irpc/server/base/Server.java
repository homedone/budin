package indiv.budin.rpc.irpc.server.base;

/**
 * @author
 * @date 2023/2/25 13 00
 * discription
 */
public interface Server {
    String getAddress();

    void run();

    void run(int port);

    void run(String host, int port);
}
