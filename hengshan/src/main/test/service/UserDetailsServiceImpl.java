package com.hengshan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hengshan.entity.User;
import com.hengshan.exception.LoginException;
import com.hengshan.mapper.MenuMapper;
import com.hengshan.mapper.UserMapper;
import com.hengshan.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new LoginException("用户不存在");
        }
        List<String> permissions = menuMapper.selectPermissionByUserId(user.getId());
        return new LoginUser(user,permissions);
    }
}
