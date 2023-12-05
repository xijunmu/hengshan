package com.hengshan.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LogManager.getLogger(LogAspect.class);

    @Pointcut(value = "@annotation(com.hengshan.common.annotation.LogAop) * *(..))") //在带有LogAop注解的方法进行切入
    public void logPointcut() {
    }

    @Pointcut("execution(* com.hengshan.controller.UserController.*(..))")
    public void userPointcut() {
    }

    @Pointcut("execution(* com.hengshan.controller..*.*(..))") //匹配目录所有类所有方法切入
    public void pointcut() {
    }

    @Before("userPointcut()")
    public void test1() {
        logger.info("Before方法进入!");
    }

    @After("userPointcut()")
    public void test2() {
        logger.info("after方法进入");
    }

    @AfterReturning(value = "userPointcut()", returning = "result")
    public void afterAdvice(JoinPoint joinPoint, Object result) {
        logger.info("增强后的返回值为:{}", result + "增强");
    }

    @AfterThrowing(value = "userPointcut()", throwing = "ex")
    public void exceptionAdvice(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        logger.error("ReqPath:{} Method:{} Exception:{}", request.getServletPath(),className+"."+methodName, ex.getMessage());
    }

    @After("logPointcut()")
    public void onLogPointcutAfter(JoinPoint joinPoint) throws Throwable {
        logger.info("LogAop",joinPoint.getSignature());
    }

}
