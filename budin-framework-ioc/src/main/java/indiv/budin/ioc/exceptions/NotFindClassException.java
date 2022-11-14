package indiv.budin.ioc.exceptions;

/**
 * @author
 * @date 2022/11/14 20 30
 * discription
 */
public class NotFindClassException extends RuntimeException{
    public NotFindClassException(String message) {
        super(message);
    }
}
