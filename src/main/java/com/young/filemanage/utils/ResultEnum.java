package com.young.filemanage.utils;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 19:54
 */
public enum ResultEnum {
    SUCCESS(200),
    PARAMS_ERROR(400),
    NO_TOKEN(401),
    NOT_FOUND(404),
    ERROR(500),
    SQL_ERROR(1001),
    EXITED(1002),
    LOGIN_ERROR(1003),
    ;
    private int code;

    private ResultEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
