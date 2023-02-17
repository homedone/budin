package indiv.budin.rpc.irpc.carrier;

public interface ResponseResult {
    boolean isSuccess();
    /**
     * 状态码
     */
    Integer getCode();
    /**
     * 状态信息
     */
    String getMessage();
}
