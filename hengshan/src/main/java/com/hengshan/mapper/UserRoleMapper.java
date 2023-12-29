package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色表(UserRole)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-27 14:27:48
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
