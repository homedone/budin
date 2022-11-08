package indiv.budin.common.constants;

import indiv.budin.common.utif.ResultCode;

public enum CommonCode implements ResultCode {
    SUCCESS(true, 1000, "成功!"),
    FAIL(false, 4000, "失败"),

    SYSTEM_ERROR(false, 3000, "系统异常"),

    UNAUTHORIZED(false,4010,"没有操作权限"),

    TOKEN_EXPIRED(false, 4030, "登陆过期,请重新登陆");

    /**
     * 状态
     */
    private final boolean status;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String message;

    CommonCode(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return status;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
