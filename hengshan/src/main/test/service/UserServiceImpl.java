package com.hengshan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Override
    public void register(User user) {
        String username = user.getName();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User u = userMapper.selectOne(queryWrapper);
        if (Objects.nonNull(u)) {
            throw new UserException("用户名已被注册");
        }
        userMapper.insert(user);
    }

    @Override
    public Page<User> selectLimit(int pageNum, int pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(User::getCreateTime);
        queryWrapper.select(User::getId,User::getName, User::getEmail,
                User::getPhone, User::getStatus, User::getCreateTime, User::getUpdateTime);
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, queryWrapper);
        return page;
    }

    public Page<User> selectLimit1(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like(User::getName, name);
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, queryWrapper);
        return page;
    }
}
