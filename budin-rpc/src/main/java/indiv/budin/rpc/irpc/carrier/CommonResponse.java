package indiv.budin.rpc.irpc.carrier;

public enum CommonResponse implements ResponseResult {
    SUCCESS(true, 1000, "success"),
    FAIL(false, 4000, "fail");
    private final boolean status;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String message;


    CommonResponse(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return status;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
