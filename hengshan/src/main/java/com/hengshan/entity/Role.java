package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 角色表(Role)表实体类
 *
 * @author muxijun
 * @since 2023-12-22 15:43:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role {

    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //角色名称
    private String name;

    //描述
    private String description;

    //显示顺序
    private Integer orderNum;

    //角色状态：0停用 1正常
    private String status;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private List<Integer> menus;
}

