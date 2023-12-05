package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Article;

import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author muxijun
 * @since 2023-10-19 12:04:07
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据ids删除多条数据
     *
     * @param ids id列表
     */
    void deleteByIds(String ids);

    /**
     * 热门文章
     *
     * return 文章列表
     */
    List<Article> hotArticle();
}

