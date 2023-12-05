package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role_menu")
public class RoleMenu {
    @TableField(value = "role_id")
    private Long roleId;
    @TableField(value = "menu_id")
    private Long menuId;

    public RoleMenu() {

    }

    public RoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
