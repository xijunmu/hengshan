package com.hengshan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hengshan.common.ResultBody;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.entity.Comment;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.CommentVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 评论表(Comment)表控制层
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/getCommentList")
    public PageVo<CommentVo> getCommonList(@RequestBody Map<String,Object> map) {
        int pageNum = (int)map.get("pageNum");
        int pageSize = (int)map.get("pageSize");
        int type = (int)map.get("type");
        int articleId = 0;
        if(!StringUtils.isEmpty(map.get("articleId"))) {
            articleId = (int)map.get("articleId");
        }
        return commentService.getList(pageNum,pageSize,type,articleId);
    }

    @PostMapping("/add")
    public ResultBody add(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

}

