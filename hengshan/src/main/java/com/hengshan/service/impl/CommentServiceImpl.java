package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.ResultBody;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.SecurityUtils;
import com.hengshan.entity.Comment;
import com.hengshan.entity.vo.CommentVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.CommentMapper;
import com.hengshan.mapper.UserMapper;
import com.hengshan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public boolean deleteByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public PageVo<CommentVo> getList(int pageNum, int pageSize, int type, int articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(type == 1, Comment::getArticleId, articleId);
        queryWrapper.eq(type != 1, Comment::getType, type);
        queryWrapper.isNull(Comment::getRootId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Comment> commentList = page.getRecords();
        List<CommentVo> list = fillUsername(commentList);
        for (CommentVo commentVo : list) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        PageVo<CommentVo> pageVo = new PageVo<>(list, page.getTotal());
        return pageVo;
    }


    public Comment getOneById(Long id) {
        return getById(id);
    }

    public Comment update(Comment comment) {
        updateById(comment);
        return comment;
    }

    public ResultBody add(Comment comment) {
        if (StringUtils.isEmpty(comment.getUserId())) {
            comment.setUserId(SecurityUtils.getUserId());
        }
        if (StringUtils.isEmpty(comment.getContent())) {
            throw new SystemException(ReturnCode.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResultBody.success();
    }

    /**
     * 根据根评论id查询子评论集合
     *
     * @param rootId 根评论id
     */
    private List<CommentVo> getChildren(Long rootId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, rootId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(queryWrapper);
        return fillUsername(commentList);
    }

    /**
     * 填充评论和被评论的用户名
     *
     * @param commentList 评论集合
     */
    private List<CommentVo> fillUsername(List<Comment> commentList) {
        List<CommentVo> list = BeanCopyUtil.copyBeanList(commentList, CommentVo.class);
        for (CommentVo commentVo : list) {
            commentVo.setUserName(userMapper.selectById(commentVo.getUserId()).getUsername());
            commentVo.setReplyUserName(userMapper.selectById(commentVo.getReplyUserId()).getUsername());
        }
        return list;
    }
}

