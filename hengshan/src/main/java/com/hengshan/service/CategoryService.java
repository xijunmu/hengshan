package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Category;

import java.util.List;

/**
 * 文章分类表(Category)表服务接口
 *
 * @author muxijun
 * @since 2023-12-06 14:51:14
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据ids删除多条数据
     *
     * @param ids id列表
     */
    void deleteByIds(String ids);

    /**
     * 类别列表
     *
     * @return 所有类别
     */
    List<Category> getList();
}

