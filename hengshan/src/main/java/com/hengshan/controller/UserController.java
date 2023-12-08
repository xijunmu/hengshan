package com.hengshan.controller;


import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表(User)表控制层
 *
 * @author muxijun
 * @since 2023-12-07 18:34:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public UserInfoVo add(@RequestBody User user) {
        userService.save(user);
        return BeanCopyUtil.copyBean(user, UserInfoVo.class);
    }

    @PostMapping("/update")
    public UserInfoVo updateById(@RequestBody User user){
        //使用updateById更新时，前端不要传时间参数，数据库时间会被unix初始时间覆盖掉
        userService.updateById(user);
        User tempUser = userService.getById(user.getId());
        return BeanCopyUtil.copyBean(tempUser, UserInfoVo.class);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Map<String,Object> map){
        List<Long> ids = (List) map.get("id");
        userService.removeByIds(ids);
    }

    @RequestMapping("/getUserList")
    public PageVo<UserInfoVo> getUserList(@RequestBody Map<String,Object> map) {
        int pageNum = (int)map.get("pageNum");
        int pageSize = (int)map.get("pageSize");
        String username = "";
        if(Objects.nonNull(map.get("username"))) {
            username = map.get("username").toString().trim();
        }
        return userService.getList(pageNum,pageSize,username);
    }

    @GetMapping("getUserById/{id}")
    public UserInfoVo selectOne(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}

