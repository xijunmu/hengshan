package com.hengshan.filter;

import com.hengshan.common.utils.JWTUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.vo.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //解析前端token,从redis里获取登录用户
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        String userId;
        try {
            Claims claims = JWTUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token解析失败");
        }
        LoginUser loginUser = (LoginUser) redisUtil.get("login:" + userId);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("redis中用户不存在!");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
