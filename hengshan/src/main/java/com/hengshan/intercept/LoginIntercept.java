package com.hengshan.intercept;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.common.ResultBody;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginIntercept implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    //自定义拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法不拦截 直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        log.info("方法{}被拦截", ((HandlerMethod) handler).getMethod().getName());

        // 从请求头里面获取token
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            // 根据header中的token，查询redis中是否有数据
            String value = (String) redisUtil.get("hengshan:login:" + token);
            // 如果能够获取到数据，说明token未过期
            if (StringUtils.hasText(value)) {
                return true;
            }
        }
        //获取不到token说明未登录，阻止该请求访问资源
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().println(JSONObject.toJSONString(ResultBody.fail(ReturnCode.INVALID_TOKEN)));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("token error：{}", e.getMessage());
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("方法被执行,但是视图还未渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("方法执行完毕,进行资源清理");
    }
}
