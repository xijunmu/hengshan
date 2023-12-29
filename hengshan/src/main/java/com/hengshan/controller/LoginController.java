package com.hengshan.controller;

import com.alibaba.fastjson2.JSONObject;
import com.hengshan.common.ResultBody;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.SecurityUtils;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.LoginUser;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.entity.vo.UserPermsVo;
import com.hengshan.service.MenuService;
import com.hengshan.service.RoleService;
import com.hengshan.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        return loginService.login(user);
    }

    @RequestMapping("/logout")
    public ResultBody logout() {
        return loginService.logout();
    }

    @RequestMapping("/getInfo")
    public UserPermsVo getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        List<String> roleNames = roleService.selectRoleByUserId(loginUser.getUser().getId());
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfoVo.class);
        return new UserPermsVo(perms, roleNames, userInfoVo);
    }

    @RequestMapping("/getRouters")
    public JSONObject getRouters() {
        Long userId = SecurityUtils.getUserId();
        JSONObject res = new JSONObject();
        res.put("menus", menuService.selectMenuTreeByUserId(userId));
        return res;
    }
}