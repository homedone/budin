package indiv.budin.common.constants;

import indiv.budin.common.utif.ResultCode;

/**
 * @author
 * @date 2022/10/26 10 11
 * discription
 */
public enum UserCenterCode implements ResultCode {
    SYSTEM_ERROR(false, 3020, "用户系统异常"),
    ACCOUNT_OR_PASSWORD_ERROR(false,2010,"用户名或密码有误"),
    BUDIN_STORAGE_ERROR(false,2020,"云盘信息有误"),
    WITHOUT_LOGIN(false,2030,"请重新登陆"),
    EMAIL_USED(false,2040,"请确认邮箱是否有效或者被注册"),

    ACCOUNT_USED(false,2050,"用户名已经被使用"),
    CODE_ERROR(false,2060,"验证码错误"),
    FAIL_SEND_CODE(false,2040,"发送验证码失败，请检查邮箱是否正确，稍后再试");

    private final boolean status;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String message;

    UserCenterCode(boolean status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    @Override
    public boolean isSuccess() {
        return this.status;
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
