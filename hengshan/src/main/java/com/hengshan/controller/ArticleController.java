package com.hengshan.controller;


import com.hengshan.entity.vo.ArticleDetailVo;
import com.hengshan.entity.vo.ArticleVo;
import com.hengshan.entity.vo.HotArticleVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ArticleService articleService;

    @RequestMapping("/hotArticleList")
    public List<HotArticleVo> getHotArticle() {
        return articleService.hotArticle();
    }

    @RequestMapping("/getArticleList")
    public PageVo<ArticleVo> getArticleList(@RequestBody Map<String,Object> map) {
        int pageNum = (int)map.get("pageNum");
        int pageSize = (int)map.get("pageSize");
        int categoryId = 0;
        if(map.get("categoryId") != null) {
            categoryId = (int)map.get("categoryId");
        }
        return articleService.getList(pageNum,pageSize,categoryId);
    }

    @RequestMapping("/getArticleDetailById/{id}")
    public ArticleDetailVo getArticleDetailById(@PathVariable("id") Integer id) {
        return articleService.getArticleById(id);
    }
}

