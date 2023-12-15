package com.hengshan.filter;

import com.alibaba.fastjson2.JSON;
import com.hengshan.common.ResultBody;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.JWTUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.common.utils.WebUtil;
import com.hengshan.entity.vo.LoginUser;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * token认证过滤器
 * 作用：解析请求头中的token。并验证合法性
 * 继承 OncePerRequestFilter 保证请求经过过滤器一次
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //解析前端token,从redis里获取登录用户
        String token = request.getHeader("token");
        // 没有token，去走登录流程
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 验证token是否合法
        Claims claims = null;
        try {
            claims = JWTUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResultBody result = ResultBody.fail(ReturnCode.INVALID_TOKEN);
            WebUtil.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();;
        LoginUser loginUser = (LoginUser) redisUtil.get("hengshan:login:" + userId);
        if (Objects.isNull(loginUser)) {
            ResultBody result = ResultBody.fail(ReturnCode.INVALID_TOKEN);
            WebUtil.renderString(response, JSON.toJSONString(result));
            log.error("exception={}","redis中用户不存在");
            return;
        }
        // 将用户信息存入 SecurityContextHolder ，以便本次在请求中使用
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
