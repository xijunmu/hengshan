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
     * 根据ids删除多条数据
     *
     * @param ids id列表
     */
    void deleteByIds(String ids);

    /**
     * 查询用户列表
     *
     * @param pageNum  第几页
     * @param pageSize 每页条数
     * @param username username模糊名称
     * @return 用户列表
     */
    PageVo<UserInfoVo> getList(int pageNum, int pageSize, String username);

    /**
     * 查询单个用户详情
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfoVo getUserById(Long id);
}

