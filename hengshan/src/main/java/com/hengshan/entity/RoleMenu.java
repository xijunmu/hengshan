package com.hengshan.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限表(RoleMenu)表实体类
 *
 * @author muxijun
 * @since 2023-12-27 14:28:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_menu")
public class RoleMenu {

    //角色ID
    private Integer roleId;

    //权限ID
    private Integer menuId;
}

