package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.mapper.ArticleMapper;
import com.hengshan.entity.Article;
import com.hengshan.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文章表(Article)表服务实现类
 *
 * @author muxijun
 * @since 2023-10-19 12:04:07
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public void deleteByIds(String ids) {
        String[] idsArr = ids.split(",");
        if (idsArr.length > 0) {
            List<Long> idList = Stream.of(idsArr).map(Long::valueOf).collect(Collectors.toList());
            articleMapper.deleteBatchIds(idList);
        }
    }

    @Override
    public List<Article> hotArticle() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,1);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> list = page.getRecords();
        return list;
    }
}

