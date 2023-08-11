package com.hengshan.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hengshan.common.ResultBody;
import com.hengshan.common.utils.AESUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.User;
import com.hengshan.exception.LoginException;
import com.hengshan.handler.LoginIntercept;
import com.hengshan.mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    private Logger logger = LogManager.getLogger(LoginIntercept.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public JSONObject login(String username, String password){
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName, username);
            queryWrapper.eq(User::getStatus, 0);
            User user = userMapper.selectOne(queryWrapper);
            if (user == null) {
                throw new LoginException("查不到该用户[" + username + "]");
            }
            if (!user.getPassword().equals(AESUtil.encrypt(password))) {
                throw new LoginException("用户名或密码不正确");
            }
            //登录成功，将生成的token存入redis中
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            redisUtil.set("hengshan:login:" + token, user.getUserId());
            JSONObject json = new JSONObject();
            json.put("username", user.getUserName());
            json.put("loginIP", user.getLoginIp());
            json.put("token", token);
            logger.info("{} login success", username);
            return json;
        } catch (Exception e) {
            logger.error("{} login failed, exception:{}", username,e);
            throw  new LoginException("登录异常");
        }
    }
}
