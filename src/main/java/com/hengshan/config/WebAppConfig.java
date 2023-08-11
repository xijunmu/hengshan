package com.hengshan.config;

import com.hengshan.handler.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private LoginIntercept loginIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginIntercept);
        // 拦截所有请求
        registration.addPathPatterns("/**");
        // 添加不拦截路径
        registration.excludePathPatterns("/login", "/error", "/logout","/**/*.html","/favicon.ico");
    }
}
