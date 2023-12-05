package com.hengshan.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.hengshan.entity.Article;
 
/**
 * 文章表(Article)表数据库访问层
 *
 * @author muxijun
 * @since 2023-10-19 12:04:07
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
 
}
