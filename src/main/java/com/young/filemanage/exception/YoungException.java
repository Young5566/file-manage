package com.young.filemanage.exception;

import com.young.filemanage.utils.ResultEnum;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 10:08
 */
public class YoungException extends RuntimeException {
    private Integer code;

    /**
     * 构造函数，确定自定义异常的状态码
     * @param resultEnum 错误类型
     * @param message 错误信息
     */
    public YoungException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
