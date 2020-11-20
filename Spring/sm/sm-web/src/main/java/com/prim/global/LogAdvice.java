package com.prim.global;

import com.prim.pojo.Log;
import com.prim.pojo.Staff;
import com.prim.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogAdvice {

    @Autowired
    private LogService logService;

    /**
     * 织入操作日志使用后置通知 监听控制器的方法操作
     *
     * @param joinPoint
     */
    @After("execution(* com.prim.controller.*.*(..)) && !execution(* com.prim.controller.SelfController.*(..)) && !execution(* com.prim.controller.*.to*(..)))")
    public void operationLog(JoinPoint joinPoint) {
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());//类名
        log.setOperation(joinPoint.getSignature().getName());//方法名
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];//方法的参数
        Staff staff = (Staff) request.getSession().getAttribute("user");
        log.setOperator(staff.getAccount());
        log.setResult("成功");
        logService.addOperationLog(log);
    }

    /**
     * 系统日志 出现异常时处理记录
     * pointcut 才是定义切点
     *
     * @param joinPoint
     */
    @AfterThrowing(throwing = "e", pointcut = "execution(* com.prim.controller.*.*(..)) && !execution(* com.prim.controller.SelfController.*(..))")
    public void systemLog(JoinPoint joinPoint, Throwable e) {
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());//类名
        log.setOperation(joinPoint.getSignature().getName());//方法名
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];//方法的参数
        Staff staff = (Staff) request.getSession().getAttribute("user");
        log.setOperator(staff.getAccount());
        log.setResult(e.getClass().getSimpleName());
        logService.addSystemLog(log);
    }

    /**
     * 登录日志
     *
     * @param joinPoint
     */
    @After("execution(* com.prim.controller.SelfController.login(..))")
    public void loginLog(JoinPoint joinPoint) {
        log(joinPoint);
    }

    @Before("execution(* com.prim.controller.SelfController.logout(..))")
    public void logoutLog(JoinPoint joinPoint) {
        log(joinPoint);
    }

    private void log(JoinPoint joinPoint) {
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());//类名
        log.setOperation(joinPoint.getSignature().getName());//方法名
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];//方法的参数
        Staff staff = (Staff) request.getSession().getAttribute("user");
        if (staff == null) {
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        } else {
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }
        logService.addLoginLog(log);
    }
}
