package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.entity.Category;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.CategoryMapper;
import com.hengshan.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章分类表(Category)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-06 14:51:14
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Transactional
    public boolean deleteByIds(List<Integer> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    public List<Category> getList() {
        return list();
    }

    public Category getOneById(Integer id) {
        return getById(id);
    }

    public Category add(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, category.getName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(ReturnCode.NAME_EXIST);
        }
        save(category);
        return category;
    }

    public Category update(Category category) {
        updateById(category);
        return category;
    }

}

