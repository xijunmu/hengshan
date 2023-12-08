package com.hengshan.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hengshan.common.ResultBody;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.JWTUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.LoginUser;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.exception.LoginException;
import com.hengshan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

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
        JSONObject json = new JSONObject();
        try {
            // 根据用户名查询用户信息
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername, user.getUsername());
            User tempUser = userMapper.selectOne(queryWrapper);
            if (Objects.isNull(tempUser)) {
                throw new RuntimeException("用户不存在");
            }
            //判断是否通过认证，spring security默认通过UserDetailsService内存认证，需实现该类重写方法走数据库认证
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authentication)) {
                throw new RuntimeException("用户名或密码错误");
            }
            // if (!tempUser.getPassword().equals(AESUtil.encrypt(user.getPassword()))) {
            //     throw new RuntimeException("用户名或密码错误");
            // }

            //认证通过，生成token，返给前端同时保存一份到redis
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            String userId = loginUser.getUser().getId().toString();
            String jwt = JWTUtil.createJWT(null, userId, null);
            redisUtil.set("hengshan:login:" + userId, loginUser);
            UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
            json.put("token", jwt);
            json.put("user", userInfoVo);
            return json;
        } catch (Exception e) {
            //logger.error("{} login failed, exception:{}", username,e);
            throw new LoginException("登录异常");
        }
    }

    /**
     * 用户登出
     */
    public ResultBody logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        redisUtil.del(loginUser.getUser().getId().toString());
        return new ResultBody(200, "退出成功");
    }
}
