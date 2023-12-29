package com.hengshan.service.impl;

import com.hengshan.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("permission")
public class PermissionService {

    /**
     * 查询用户是否有权限
     *
     * @param permission 权限
     * @return boolean
     */
    public boolean hasPermission(String permission) {
        // 如果是管理员就返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permissions);
    }
}
