package com.hengshan.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.common.ResultBody;
import com.hengshan.entity.User;
import com.hengshan.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginService loginService;


    @RequestMapping("/login")
    public JSONObject login(@RequestBody User user) {
        JSONObject resp = loginService.login(user);
        return resp;
    }

    @RequestMapping("/logout")
    public ResultBody logout(){
        return loginService.logout();
    }
}