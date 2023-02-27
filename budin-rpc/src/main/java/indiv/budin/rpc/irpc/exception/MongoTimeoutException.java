package indiv.budin.rpc.irpc.exception;

/**
 * @author
 * @date 2023/2/27 19 16
 * discription
 */
public class MongoTimeoutException extends RuntimeException{
    public MongoTimeoutException(String message) {
        super(message);
    }
}
