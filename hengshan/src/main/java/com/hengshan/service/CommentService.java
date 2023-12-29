package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Comment;
import com.hengshan.entity.vo.CommentVo;
import com.hengshan.entity.vo.PageVo;

/**
 * 评论表(Comment)表服务接口
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
public interface CommentService extends IService<Comment> {

    /**
     * 获取评论列表
     *
     * @return 评论列表
     */
    PageVo<CommentVo> getList(int pageNum, int pageSize, int type, int articleId);

}

