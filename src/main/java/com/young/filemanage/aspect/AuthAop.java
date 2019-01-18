package com.young.filemanage.aspect;

import com.young.filemanage.entity.Result;
import com.young.filemanage.entity.User;
import com.young.filemanage.service.UserService;
import com.young.filemanage.utils.JWT;
import com.young.filemanage.utils.ResultEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 11:51
 */
@Aspect
@Component
public class AuthAop {

    @Autowired
    private UserService userService;

    /**
     * 需要请求验证的接口函数
     */
    @Pointcut("execution(public * com.young.filemanage.controller.*.*(..))"
            + "&& !execution(public * com.young.filemanage.controller.UserController.login(..))"
            + "&& !execution(public * com.young.filemanage.controller.FileController.uploadFile(..))"
            + "&& !execution(public * com.young.filemanage.controller.FileController.downloadFile(..))"
            + "&& !execution(public * com.young.filemanage.controller.FileController.deleteFile(..))"
    )
    public void pointCut(){

    }

    /**
     * 用于验证是否用token，且token的可用性
     * @param pj 线程放行
     * @return 返回错误或放行
     * @throws Throwable Throwable类是整个异常体系类的“父级类
     */
    @Around("pointCut()")
    public Object Handler(ProceedingJoinPoint pj) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String token = request.getHeader("token");
        if(token != null && !token.equals("")){
            Map res = JWT.verifiyToken(token);
            if(res != null){
                User user = userService.getByUsername(res.get("username").toString());
                if(user != null){
                    return pj.proceed();
                } else {
                    return new Result(ResultEnum.NO_TOKEN, "token错误");
                }
            } else {
                return new Result<>(ResultEnum.NO_TOKEN, "token错误");
            }
        } else {
            return new Result<>(ResultEnum.NO_TOKEN, "请添加token");
        }
    }
}
