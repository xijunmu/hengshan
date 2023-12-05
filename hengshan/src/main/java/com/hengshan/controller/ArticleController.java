package com.hengshan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hengshan.entity.Article;
import com.hengshan.mapper.ArticleMapper;
import com.hengshan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 文章表(Article)表控制层
 *
 * @author muxijun
 * @since 2023-10-19 12:11:22
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list")
    public List<Article> selectHotArticle() {
        return articleService.hotArticle();
    }

}

