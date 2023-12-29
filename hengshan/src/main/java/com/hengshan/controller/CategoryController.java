package com.hengshan.controller;


import com.hengshan.entity.Category;
import com.hengshan.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private CategoryServiceImpl categoryService;

    @PostMapping("/getList")
    public List<Category> getList() {
        return categoryService.getList();
    }

    @GetMapping("getById/{id}")
    public Category getById(@PathVariable("id") Integer id) {
        return categoryService.getOneById(id);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id");
        return categoryService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Integer id) {
        return categoryService.deleteById(id);
    }

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @PostMapping("/update")
    public Category updateById(@RequestBody Category category) {
        return categoryService.update(category);
    }
}

