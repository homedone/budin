package indiv.budin.common.constants;

import indiv.budin.common.utif.ResultCode;

public enum FileServiceCode implements ResultCode {
    PATH_NAME_ERROR(false, 4010, "路径错误"),
    SYSTEM_ERROR(false, 3010, "系统异常"),
    FILE_OVER_SIZE(false, 4011, "文件太大了,不能超出" + FileServiceConstant.MAX_FILE_SIZE + FileServiceConstant.FILE_UINT);
    private final boolean status;
    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态信息
     */
    private final String message;

    FileServiceCode(boolean status, int code, String message) {
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
