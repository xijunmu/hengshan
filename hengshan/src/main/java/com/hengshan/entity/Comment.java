package com.hengshan.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论表(Comment)表实体类
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment {

    //评论id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //文章id
    private Integer articleId;

    //类型：1文章 2友链
    private String type;

    //根评论id
    private Long rootId;

    //被回复的评论id
    private Long replyId;

    //评论内容
    private String content;

    //被回复的用户id
    private Long replyUserId;

    //当前评论用户id
    private Long userId;

    //评论时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

