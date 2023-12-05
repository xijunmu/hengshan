package com.hengshan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hengshan.entity.User;
import com.hengshan.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class HengshanApplicationTests {

    @Autowired
    public UserMapper userMapper;

    @Test
    public void getById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void pageTest() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("user_id");
        LambdaQueryWrapper<User> wrapper1 = Wrappers.lambdaQuery(User.class);
        wrapper1.like(User::getName, "aa");
        wrapper1.lt(User::getId, 4);
        // 创建分页对象（1表示第一页；4表示每页大小为4）
        Page<User> page = new Page<>(1, 2);
        userMapper.selectPage(page, wrapper);
        System.out.println("size: " + page.getSize());
        System.out.println("total: " + page.getTotal());
        for(User user : page.getRecords()) {
            System.out.println(user);
        }
    }

    @Test
    public void pageTest2() {
        Page<Map<String,Object>> page = new Page<>(1, 4);
        userMapper.selectMapsPage(page, null);
        System.out.println("size: " + page.getSize());
        System.out.println("total: " + page.getTotal());
        System.out.println("pages: " + page.getPages());
        for(Map<String,Object> map : page.getRecords()) {
            System.out.println(map);
        }
    }

    @Test
    void contextLoads() {
    }

}
