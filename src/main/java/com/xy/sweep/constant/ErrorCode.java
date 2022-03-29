package com.xy.sweep.constant;

/**
 * @author climb.xu
 * @date 2022/3/29 15:20
 */
public enum ErrorCode {
    EMAIL_EXIST(5001, "邮箱已存在");

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final Integer code;
    public final String msg;

}
