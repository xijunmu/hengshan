package com.hengshan.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    //评论id
    private Long id;

    //根评论id
    private Long rootId;

    //被回复的评论id
    private Long replyId;

    //评论内容
    private String content;

    //被回复的用户id
    private Long replyUserId;

    //被回复的用户
    private String replyUserName;

    //当前评论用户id
    private Long userId;

    //当前评论用户
    private String userName;

    // 子评论
    private List<CommentVo> children;

    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
