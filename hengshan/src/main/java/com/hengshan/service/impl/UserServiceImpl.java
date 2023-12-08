package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.entity.Article;
import com.hengshan.entity.Category;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.ArticleDetailVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.mapper.UserMapper;
import com.hengshan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户表(User)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-07 18:34:20
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 正常用户
    private static final int USER_STATUS_NORMAL = 1;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteByIds(String ids) {
        String[] idsArr = ids.split(",");
        if (idsArr.length > 0) {
            List<Long> idList = Stream.of(idsArr).map(Long::valueOf).collect(Collectors.toList());
            userMapper.deleteBatchIds(idList);
        }
    }

    @Override
    public PageVo<UserInfoVo> getList(int pageNum, int pageSize, String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStatus, USER_STATUS_NORMAL);
        queryWrapper.like(!username.isEmpty(), User::getUsername, username);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<UserInfoVo> list = BeanCopyUtil.copyBeanList(page.getRecords(), UserInfoVo.class);
        PageVo<UserInfoVo> pageVo = new PageVo<>(list, page.getTotal());
        return pageVo;
    }

    @Override
    public UserInfoVo getUserById(Long id) {
        User user = userMapper.selectById(id);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        return userInfoVo;
    }
}

