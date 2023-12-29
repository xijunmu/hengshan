package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限表(RoleMenu)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-27 14:28:12
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
