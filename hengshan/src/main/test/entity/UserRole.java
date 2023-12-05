package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_role")
public class UserRole {

    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "role_id")
    private Long roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
