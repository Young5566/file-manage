package com.young.filemanage.entity;

import com.young.filemanage.utils.ResultEnum;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 20:04
 */
public class Result<T> {
    private int code;  // 状态码
    private String msg; //返回信息
    private T data;     // 返回数据

    /**
     * 用于返回结果，光返回数据则认为式操作成功
     * @param data 需要返回的数据
     */
    public Result(T data) {
        this(ResultEnum.SUCCESS.getCode(), "success", data);
    }

    public Result(ResultEnum resultEnum, String msg) {
        this(resultEnum.getCode(), msg, null);
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
