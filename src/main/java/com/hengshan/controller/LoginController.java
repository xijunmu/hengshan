package com.hengshan.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map<String,Object> map) {
        JSONObject json = loginService.login((String)map.get("userName"),(String)map.get("password"));
        return json;
    }

    @PostMapping("/logout")
    @ResponseBody
    public void logout(){
        System.out.println("logout...");
    }
}