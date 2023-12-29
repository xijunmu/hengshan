package com.hengshan.service.impl;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.entity.RoleMenu;
import com.hengshan.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 * 角色权限表(RoleMenu)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-27 14:28:12
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IService<RoleMenu> {

}

