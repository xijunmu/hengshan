package com.hengshan.runner;

import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.Article;
import com.hengshan.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MyStartRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    // springboot启动时执行自定义的一些东西
    @Override
    public void run(String... args) throws Exception {
        Map<String, Integer> viewCount = getViewCount();
        redisUtil.set("article:viewCount", viewCount);
    }

    /**
     * 获取文章访问量
     */
    public Map<String, Integer> getViewCount() {
        List<Article> list = articleMapper.selectList(null);
        return list.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount()));
    }
}
