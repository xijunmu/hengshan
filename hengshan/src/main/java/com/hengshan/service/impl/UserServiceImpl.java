package com.hengshan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.entity.User;
import com.hengshan.entity.UserRole;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.UserMapper;
import com.hengshan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Transactional
    public boolean deleteByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public PageVo<UserInfoVo> getList(int pageNum, int pageSize, String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStatus, USER_STATUS_NORMAL);
        queryWrapper.like(!username.isEmpty(), User::getUsername, username);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<UserInfoVo> list = BeanCopyUtil.copyBeanList(page.getRecords(), UserInfoVo.class);
        return new PageVo<>(list, page.getTotal());
    }

    @Override
    public UserInfoVo getOneById(Long id) {
        User user = userMapper.selectById(id);
        return BeanCopyUtil.copyBean(user, UserInfoVo.class);
    }

    @Override
    @Transactional
    public UserInfoVo add(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(ReturnCode.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(ReturnCode.PASSWORD_NOT_NULL);
        }
        //查询数据库用户名是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        if (count(queryWrapper) > 0) {
            throw new SystemException(ReturnCode.USERNAME_EXIST);
        }
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        //添加用户角色关联信息
        List<UserRole> userRoles = user.getRoles().stream()
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        User tempUser = userMapper.selectById(user.getId());
        return BeanCopyUtil.copyBean(tempUser, UserInfoVo.class);
    }

    @Override
    @Transactional
    public UserInfoVo update(User user) {
        //使用updateById更新时，前端不要传时间参数，数据库时间会被unix初始时间覆盖掉
        updateById(user);
        if(!user.getRoles().isEmpty()){
            List<UserRole> userRoles = user.getRoles().stream()
                    .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
            LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
            userRoleService.remove(queryWrapper.eq(UserRole::getUserId, user.getId()));
            userRoleService.saveBatch(userRoles);
        }
        User tempUser = userMapper.selectById(user.getId());
        return BeanCopyUtil.copyBean(tempUser, UserInfoVo.class);
    }
}

