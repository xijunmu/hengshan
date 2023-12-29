package com.hengshan.controller;


import com.hengshan.entity.Article;
import com.hengshan.entity.vo.ArticleDetailVo;
import com.hengshan.entity.vo.ArticleVo;
import com.hengshan.entity.vo.HotArticleVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private ArticleServiceImpl articleService;

    @RequestMapping("/getHotArticleList")
    public List<HotArticleVo> getHotArticleList() {
        return articleService.hotArticle();
    }

    @RequestMapping("/getList")
    public PageVo<ArticleVo> getList(@RequestBody Map<String, Object> map) {
        int pageNum = (int) map.get("pageNum");
        int pageSize = (int) map.get("pageSize");
        int categoryId = 0;
        if (map.get("categoryId") != null) {
            categoryId = (int) map.get("categoryId");
        }
        return articleService.getList(pageNum, pageSize, categoryId);
    }

    @RequestMapping("/getById/{id}")
    public ArticleDetailVo getById(@PathVariable("id") Integer id) {
        return articleService.getOneById(id);
    }

    @RequestMapping("/updateViewCount/{id}")
    public void updateViewCount(@PathVariable("id") Integer id) {
        articleService.updateViewCount(id);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id");
        return articleService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Integer id) {
        return articleService.deleteById(id);
    }

    @PostMapping("/add")
    public Article add(@RequestBody Article article) {
        return articleService.add(article);
    }

    @PostMapping("/update")
    public Article update(@RequestBody Article article) {
        return articleService.update(article);
    }
}

