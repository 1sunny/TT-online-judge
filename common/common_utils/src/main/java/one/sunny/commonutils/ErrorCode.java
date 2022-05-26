package one.sunny.commonutils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    TOKEN_ERROR(5001, "token error: "),
    FILE_ERROR(5002, "file error: "),
    PARAM_ERROR(5003, "param error: "),
    INSERT_ERROR(5004, "insert error: "),
    UPDATE_ERROR(5005, "update error: "),
    NOT_FOUND_ERROR(5005, "not fount error: ");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
