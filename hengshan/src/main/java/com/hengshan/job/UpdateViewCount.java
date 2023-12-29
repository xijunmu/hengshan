package com.hengshan.job;

import com.hengshan.common.utils.RedisUtil;
import com.hengshan.entity.Article;
import com.hengshan.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCount {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ArticleService articleService;

    // 0 * * * * ?
    @Scheduled(cron = "0 */30 * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = (Map<String, Integer>) redisUtil.get("article:viewCount");
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Integer.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);

    }
}
