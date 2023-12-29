package com.hengshan.common.aop;

import com.alibaba.fastjson2.JSON;
import com.hengshan.common.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {

    //在带有LogAop注解的方法进行切入
    @Pointcut("@annotation(com.hengshan.common.annotation.SystemLog)")
    public void logPointcut() {
    }

    //匹配指定类所有方法切入
    @Pointcut("execution(* com.hengshan.controller.UserController.*(..))")
    public void userPointcut() {
    }

    //匹配指定目录所有类所有方法切入
    @Pointcut("execution(* com.hengshan.controller..*.*(..))")
    public void allPointcut() {
    }


    @Around("logPointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("======End======" + System.lineSeparator());
        }
        return ret;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上的注解对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        String methodName = methodSignature.getName();
        String className = methodSignature.getDeclaringTypeName();
        log.info("======Start======");
        // 打印请求 URL
        log.info("URL           : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName  : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method   : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method  : {}.{}", className, methodName);
        // 打印请求的 IP
        log.info("IP            : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args  : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

}
