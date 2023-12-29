package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.Article;
import com.hengshan.entity.Category;
import com.hengshan.entity.vo.ArticleDetailVo;
import com.hengshan.entity.vo.ArticleVo;
import com.hengshan.entity.vo.HotArticleVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.mapper.ArticleMapper;
import com.hengshan.mapper.CategoryMapper;
import com.hengshan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author muxijun
 * @since 2023-10-19 12:04:07
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    // 正式文章
    private static final int ARTICLE_STATUS_NORMAL = 1;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<HotArticleVo> hotArticle() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);
        List<Article> list = page.getRecords();
        return BeanCopyUtil.copyBeanList(list, HotArticleVo.class);
    }

    @Override
    public PageVo<ArticleVo> getList(int pageNum, int pageSize, int categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        queryWrapper.eq(categoryId != 0, Article::getCategoryId, categoryId);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<ArticleVo> list = BeanCopyUtil.copyBeanList(page.getRecords(), ArticleVo.class);
        for (ArticleVo articleVo : list) {
            Category category = categoryMapper.selectById(articleVo.getCategoryId());
            articleVo.setCategoryName(category.getName());
        }
        PageVo<ArticleVo> pageVo = new PageVo<>(list, page.getTotal());
        return pageVo;
    }

    @Override
    public ArticleDetailVo getOneById(Integer id) {
        Article article = getById(id);
        Integer viewCount = redisUtil.getMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount);
        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class);
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return articleDetailVo;
    }

    @Override
    public void updateViewCount(Integer id) {
        //更新redis中对应id的浏览量
        redisUtil.incrementMapValue("article:viewCount", id.toString(), 1);
    }

    @Transactional
    public boolean deleteByIds(List<Integer> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    public Article update(Article article) {
        updateById(article);
        return article;
    }

    public Article add(Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        save(article);
        return getById(article.getId());
    }

}

