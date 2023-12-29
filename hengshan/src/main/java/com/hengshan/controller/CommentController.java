package com.hengshan.controller;


import com.hengshan.common.ResultBody;
import com.hengshan.entity.Comment;
import com.hengshan.entity.vo.CommentVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private CommentServiceImpl commentService;

    @RequestMapping("/getList")
    public PageVo<CommentVo> getList(@RequestBody Map<String, Object> map) {
        int pageNum = (int) map.get("pageNum");
        int pageSize = (int) map.get("pageSize");
        int type = (int) map.get("type");
        int articleId = 0;
        if (!StringUtils.isEmpty(map.get("articleId"))) {
            articleId = (int) map.get("articleId");
        }
        return commentService.getList(pageNum, pageSize, type, articleId);
    }

    @GetMapping("getById/{id}")
    public Comment getById(@PathVariable("id") Long id) {
        return commentService.getOneById(id);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Long> ids = (List<Long>) map.get("id");
        return commentService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        return commentService.deleteById(id);
    }

    @PostMapping("/add")
    public ResultBody add(@RequestBody Comment comment) {
        return commentService.add(comment);
    }

    @PostMapping("/update")
    public Comment updateById(@RequestBody Comment comment) {
        return commentService.update(comment);
    }

}

