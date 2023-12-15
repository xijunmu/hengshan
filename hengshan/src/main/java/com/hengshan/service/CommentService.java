package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.common.ResultBody;
import com.hengshan.entity.Comment;
import com.hengshan.entity.vo.CommentVo;
import com.hengshan.entity.vo.PageVo;

import java.util.List;

/**
 * 评论表(Comment)表服务接口
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据ids删除多条数据
     *
     * @param ids id列表
     */
    void deleteByIds(String ids);

    /**
     * 获取评论列表
     * @return 评论列表
     */
    PageVo<CommentVo> getList(int pageNum, int pageSize, int type, int articleId);

    /**
     * 添加评论
     */
    ResultBody addComment(Comment comment);
}

