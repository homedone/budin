package indiv.budin.common.constants;

import indiv.budin.common.utif.ResultCode;

/**
 * @author
 * @date 2022/10/31 21 32
 * discription
 */
public class ExtendCode implements ResultCode {
    private final boolean status;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String message;

    public ExtendCode(boolean status, int code, String message) {
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
