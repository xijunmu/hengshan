package com.hengshan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文章表(Article)表实体类
 *
 * @author muxijun
 * @since 2023-10-19 12:04:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article {

    //ID
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //标题
    private String title;

    //内容
    private String content;

    //摘要
    private String summary;

    //所属分类id
    private Integer categoryId;

    //缩略图
    private String thumbnail;

    //访问量
    private Integer viewCount;

    //评论数
    private Integer commentCount;

    //是否置顶：0否 1是
    private String isTop;

    //状态：0草稿 1已发布
    private String status;

    //是否允许评论：0否 1是
    private String isComment;

    //删除标志：0正常 1删除
    private String delFlag;

    //创建文章用户
    private Long userId;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Article(Integer id, Integer viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}

