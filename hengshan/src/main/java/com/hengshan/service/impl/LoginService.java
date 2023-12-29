package com.hengshan.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.common.ResultBody;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.JWTUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.LoginUser;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户登录
     *
     * @param user 登录用户信息
     */
    public JSONObject login(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(ReturnCode.REQUIRE_USERNAME);
        }
        //判断是否通过认证，spring security默认通过UserDetailsService内存认证，需实现该类重写方法走数据库认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //认证通过，生成token，返给前端同时保存一份到redis
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JWTUtil.createJWT(userId, null);
        redisUtil.set("hengshan:login:" + userId, loginUser);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        JSONObject json = new JSONObject();
        json.put("token", jwt);
        json.put("userinfo", userInfoVo);
        return json;
    }

    /**
     * 用户登出
     */
    public ResultBody logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        redisUtil.del("hengshan:login:" + loginUser.getUser().getId());
        return ResultBody.success();
    }
}
