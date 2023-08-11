package com.hengshan.handler;

import com.hengshan.common.ResultBody;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class LoginIntercept implements HandlerInterceptor {
    private Logger logger = LogManager.getLogger(LoginIntercept.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        logger.info("方法{}被拦截", ((HandlerMethod) handler).getMethod().getName());

        // 从请求头里面获取token
        String token = request.getHeader("TOKEN");
        if (StringUtils.hasText(token)) {
            // 根据header中的token，查询redis中是否有数据
            String value = (String)redisUtil.get("hengshan:login:"+token);
            // 如果能够获取到数据，说明token未过期
            if (StringUtils.hasText(value)) {
                return true;
            }
        }
        //获取不到token说明未登录，阻止该请求访问资源
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.println(ResultBody.fail(ReturnCode.TOKEN_AUTHENTICATION_FAILED));
        } catch (IOException e) {
            logger.error("login token error is {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("方法被执行,但是视图还未渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("方法执行完毕,进行资源清理");
    }
}
