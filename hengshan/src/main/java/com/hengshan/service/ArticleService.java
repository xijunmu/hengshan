package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Article;
import com.hengshan.entity.vo.ArticleDetailVo;
import com.hengshan.entity.vo.ArticleVo;
import com.hengshan.entity.vo.HotArticleVo;
import com.hengshan.entity.vo.PageVo;

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
     * @return 热门文章列表
     */
    List<HotArticleVo> hotArticle();

    /**
     * 文章列表
     *
     * @param pageNum    第几页
     * @param pageSize   每页条数
     * @param categoryId 文章类别id
     * @return 所有文章列表
     */
    PageVo<ArticleVo> getList(int pageNum, int pageSize, int categoryId);

    /**
     * 文章详情
     *
     * @param id 文章id
     * @return 文章详情
     */
    ArticleDetailVo getArticleById(Integer id);
}

