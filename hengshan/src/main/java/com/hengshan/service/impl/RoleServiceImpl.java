package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.entity.Role;
import com.hengshan.entity.RoleMenu;
import com.hengshan.entity.UserRole;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.RoleMapper;
import com.hengshan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表(Role)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-22 15:43:24
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuServiceImpl roleMenuService;

    @Transactional
    public boolean deleteByIds(List<Integer> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    public PageVo<Role> getList(int pageNum, int pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, null);
        return new PageVo<>(page.getRecords(), page.getTotal());
    }

    public Role getOneById(Integer id) {
        return getById(id);
    }

    @Transactional
    public Role add(Role role) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, role.getName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(ReturnCode.ROLE_EXIST);
        }
        save(role);
        //添加角色权限关联信息
        List<RoleMenu> roleMenus = role.getMenus().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return getById(role.getId());
    }

    @Transactional
    public Role update(Role role) {
        updateById(role);
        if(!role.getMenus().isEmpty()){
            List<RoleMenu> roleMenus = role.getMenus().stream()
                    .map(menuId -> new RoleMenu(role.getId(), menuId)).collect(Collectors.toList());
            LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            roleMenuService.remove(queryWrapper.eq(RoleMenu::getRoleId, role.getId()));
            roleMenuService.saveBatch(roleMenus);
        }
        return role;
    }

    @Override
    public List<String> selectRoleByUserId(Long userId) {
        List<String> role = new ArrayList<>();
        if (userId.equals(1L)) {
            role.add("超级管理员");
            return role;
        }
        return roleMapper.selectRoleByUserId(userId);
    }
}

