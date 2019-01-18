package com.young.filemanage.handle;

import com.young.filemanage.entity.Result;
import com.young.filemanage.exception.YoungException;
import com.young.filemanage.utils.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 10:11
 */

@RestControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(Exception.class);

    /**
     * 全局异常拦截
     * @param e 异常
     * @return 返回response
     */
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e){
        if(e instanceof YoungException){
            // 强制转化为自定义异常，然后获取状态码及信息并返回
            YoungException youngException = (YoungException) e;
            return new Result(youngException.getCode(), youngException.getMessage());
        } else if(e instanceof ConstraintViolationException){
            // 强制转化为参数错误异常，然后获取错误信息并返回
            ConstraintViolationException paramsError = (ConstraintViolationException) e;
            return new Result(ResultEnum.PARAMS_ERROR, paramsError.getMessage().split(",")[0].split(":")[1]);
        } else {
            // 不知道的异常类型
            logger.error("【系统异常】：");
            e.printStackTrace();
            return new Result(ResultEnum.ERROR,"未知异常");
        }
    }
}
