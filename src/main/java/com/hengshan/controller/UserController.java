package com.hengshan.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hengshan.entity.User;
import com.hengshan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public User add(@RequestBody User user) {
        userService.save(user);
        return userService.getById(user.getUserId());
    }

    @PostMapping("/update")
    @ResponseBody
    public User updateById(@RequestBody User user){
        //使用LambdaUpdateWrapper时，每次更新时密码无法自动加密
        //LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        //updateWrapper.eq(User::getUserId,user.getUserId());
        //updateWrapper.set(User::getUserName,user.getUserName());
        //updateWrapper.set(User::getPassword,user.getPassword());
        //updateWrapper.set(User::getEmail,user.getEmail());
        //updateWrapper.set(User::getPhoneNumber,user.getPhoneNumber());
        //userService.update(user,updateWrapper);
        //使用updateById更新时，前端不要传时间参数，数据库时间会被unix初始时间覆盖掉
        userService.updateById(user);
        return userService.getById(user.getUserId());
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestBody Map<String,Object> map){
        List ids = (List) map.get("id");
        userService.removeByIds(ids);
    }

    @GetMapping("getInfo/{id}")
    @ResponseBody
    public User selectOne(@PathVariable int id) {
        return userService.getById(id);
    }

    @RequestMapping("/select")
    public Page<User> selectPage(@RequestBody Map<String,Object> map) {
        int pageNum = (int)map.get("pageNum");
        int pageSize = (int)map.get("pageSize");
        Page<User> page = userService.selectLimit(pageNum, pageSize);
        return page;
    }

    @RequestMapping("/select1")
    public Page<User> selectPage1(@RequestBody Map<String,Object> map) {
        int pageNum = (int)map.get("pageNum");
        int pageSize = (int)map.get("pageSize");
        String name = (String)map.get("name");
        Page<User> page = userService.selectLimit1(pageNum, pageSize, name);
        return page;
    }

    @GetMapping("/error")
    public int error() {
        int i = 9 / 0;
        return i;
    }
}