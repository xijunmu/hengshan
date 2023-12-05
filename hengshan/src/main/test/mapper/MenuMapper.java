package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select m.* from user_role ur left join role r on ur.role_id=r.id " +
            "left join role_menu rm on r.id=rm.role_id left join menu m on rm.menu_id=m.id where user_id=#{userId}")
    List<String> selectPermissionByUserId(@Param("userId") Long userId);
}
