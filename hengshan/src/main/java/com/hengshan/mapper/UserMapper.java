package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-07 18:34:20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
