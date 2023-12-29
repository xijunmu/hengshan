package com.hengshan.service.impl;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.entity.UserRole;
import com.hengshan.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * 用户角色表(UserRole)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-27 14:27:48
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IService<UserRole> {

}

