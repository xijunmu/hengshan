package com.hengshan.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友链表(BroLink)表实体类
 *
 * @author muxijun
 * @since 2023-12-07 16:14:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bro_link")
public class BroLink {

    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //名称
    private String name;

    //描述
    private String description;

    //地址
    private String url;

    //logo
    private String logo;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}

