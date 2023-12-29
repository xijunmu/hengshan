package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文章分类表(Category)表实体类
 *
 * @author muxijun
 * @since 2023-12-06 14:51:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category {

    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //分类名称
    private String name;

    //描述信息
    private String description;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

