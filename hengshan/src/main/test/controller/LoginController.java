package com.hengshan.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.common.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject req) {
        JSONObject resp = loginService.login(req.getString("userName"),req.getString("password"));
        return resp;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResultBody logout(){
        return loginService.logout();
    }
}