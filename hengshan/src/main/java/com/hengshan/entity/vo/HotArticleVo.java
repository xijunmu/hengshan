package com.hengshan.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {

    //ID
    private Integer id;

    //标题
    private String title;

    //访问量
    private Integer viewCount;
}
