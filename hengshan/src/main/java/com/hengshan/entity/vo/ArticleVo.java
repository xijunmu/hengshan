package com.hengshan.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo {

    //ID
    private Integer id;

    //标题
    private String title;

    //摘要
    private String summary;

    //所属分类ID
    private Integer categoryId;

    //所属分类名称
    private String categoryName;

    //缩略图
    private String thumbnail;

    //访问量
    private Integer viewCount;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
