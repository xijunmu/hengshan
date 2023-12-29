package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author muxijun
 * @since 2023-10-19 12:04:07
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
