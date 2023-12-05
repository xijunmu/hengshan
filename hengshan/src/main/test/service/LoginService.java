package com.hengshan.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hengshan.common.ResultBody;
import com.hengshan.common.utils.AESUtil;
import com.hengshan.common.utils.JWTUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.User;
import com.hengshan.exception.LoginException;
import com.hengshan.intercept.LoginIntercept;
import com.hengshan.mapper.UserMapper;
import com.hengshan.vo.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    private final Logger logger = LogManager.getLogger(LoginIntercept.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用户登录,spring security认证
     * @param username 用户名
     * @param password 用户密码
     */
    public JSONObject login(String username, String password){
        JSONObject json = new JSONObject();
        try {
            //用户认证
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if(authentication == null){
                throw new LoginException("用户名或密码错误");
            }
            //认证通过，使用userid生成jwt，返给前端
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            String userId = loginUser.getUser().getId().toString();
            String jwt = JWTUtil.createJWT(null, userId,null);
            json.put("token", jwt);
            //把完整用户信息存入redis
            redisUtil.set("login:" + userId, loginUser);
            logger.info("{} login success", username);
            return json;
        } catch (Exception e) {
            logger.error("{} login failed, exception:{}", username,e);
            throw  new LoginException("登录异常");
        }
    }

    /**
     * 用户登出
     */
    public ResultBody logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        redisUtil.del(loginUser.getUser().getId().toString());
        return new ResultBody(200,"退出成功");
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public JSONObject login1(String username, String password){
        JSONObject json = new JSONObject();
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getName, username);
            queryWrapper.eq(User::getStatus, 0);
            User user = userMapper.selectOne(queryWrapper);
            if (user == null) {
                throw new LoginException("用户名或密码错误");
            }
            if (!user.getPassword().equals(AESUtil.encrypt(password))) {
                throw new LoginException("用户名或密码错误");
            }
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            redisUtil.set("hengshan:login:" + token, user.getId());
            json.put("token", token);
            logger.info("{} login success", username);
            return json;
        } catch (Exception e) {
            logger.error("{} login failed, exception:{}", username,e);
            throw  new LoginException("登录异常");
        }
    }
}
