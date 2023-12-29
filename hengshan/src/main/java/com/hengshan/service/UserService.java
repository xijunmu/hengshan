package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;

/**
 * 用户表(User)表服务接口
 *
 * @author muxijun
 * @since 2023-12-07 18:34:20
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户列表
     *
     * @param pageNum  第几页
     * @param pageSize 每页条数
     * @param username 模糊名称
     */
    PageVo<UserInfoVo> getList(int pageNum, int pageSize, String username);

    /**
     * 查询单个用户详情
     *
     * @param id 用户id
     */
    UserInfoVo getOneById(Long id);

    /**
     * 添加用户
     *
     * @param user 用户
     */
    UserInfoVo add(User user);

    /**
     * 更新用户
     *
     * @param user 用户
     */
    UserInfoVo update(User user);
}

