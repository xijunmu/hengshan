package com.hengshan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.entity.User;
import com.hengshan.exception.UserExistException;
import com.hengshan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Override
    public void register(User user) {
        String username = user.getUserName();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User u = userMapper.selectOne(queryWrapper);
        if (u != null) {
            throw new UserExistException("用户名已经被注册");
        }
        userMapper.insert(user);
    }

    @Override
    public Page<User> selectLimit(int pageNum, int pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(User::getCreateTime);
        queryWrapper.select(User::getUserName, User::getUserType, User::getEmail, User::getPhoneNumber, User::getStatus, User::getCreateTime, User::getUpdateTime);
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, queryWrapper);
        return page;
    }

    public Page<User> selectLimit1(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !"".equals(name)) {
            queryWrapper.like(User::getUserName, name);
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> page = new Page<>(pageNum, pageSize);
        userMapper.selectPage(page, queryWrapper);
        return page;
    }
}
