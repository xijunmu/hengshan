package com.hengshan.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.hengshan.entity.Comment;
 
/**
 * 评论表(Comment)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-12 14:38:46
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
