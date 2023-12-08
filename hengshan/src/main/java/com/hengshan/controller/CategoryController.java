package com.hengshan.controller;


import com.hengshan.entity.Category;
import com.hengshan.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章分类表(Category)表控制层
 *
 * @author muxijun
 * @since 2023-12-06 14:51:14
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/getCategoryList")
    public List<Category> getCategoryList() {
        return categoryService.getList();
    }


}

