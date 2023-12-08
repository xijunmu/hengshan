package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户表(User)表实体类
 *
 * @author muxijun
 * @since 2023-12-07 18:41:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //用户名
    private String username;

    //密码
    private String password;

    //头像
    private String avatar;

    //邮箱
    private String email;

    //手机号码
    private String phone;

    //用户类型：0普通用户 1管理员
    private String type;

    //帐号状态：0停用 1正常
    private String status;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

