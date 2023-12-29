package com.hengshan.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.hengshan.entity.Role;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色表(Role)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-22 15:43:24
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Results(id = "roleInfo", value = {
            @Result(column = "name", property = "roleName")
    })
    @Select("select r.name from user_role ur left join role r on ur.role_id=r.id where ur.user_id=#{userId} and r.status='1'")
    List<String> selectRoleByUserId(Long userId);
}
