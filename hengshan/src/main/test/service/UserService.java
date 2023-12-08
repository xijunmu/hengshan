package com.hengshan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 分页查询
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     */
    Page<User> selectLimit(int pageNum, int pageSize);

    /**
     * 带条件分页查询
     *
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @param name     名字匹配
     */

    Page<User> selectLimit1(int pageNum, int pageSize, String name);
}
