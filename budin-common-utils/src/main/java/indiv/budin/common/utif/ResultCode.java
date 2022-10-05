package indiv.budin.common.utif;

public interface ResultCode {
    /**
     * 判断状态
     */
    boolean isSuccess();
    /**
     * 状态码
     */
    int getCode();
    /**
     * 状态信息
     */
    String getMessage();
}
