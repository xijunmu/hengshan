package com.hengshan.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表(UserRole)表实体类
 *
 * @author muxijun
 * @since 2023-12-27 14:27:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role")
public class UserRole {

    //用户ID
    private Long userId;

    //角色ID
    private Integer roleId;
}

