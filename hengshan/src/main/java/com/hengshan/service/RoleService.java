package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Role;

import java.util.List;

/**
 * 角色表(Role)表服务接口
 *
 * @author muxijun
 * @since 2023-12-22 15:43:24
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询用户角色
     *
     * @param userId  用户id
     */
    List<String> selectRoleByUserId(Long userId);
}

