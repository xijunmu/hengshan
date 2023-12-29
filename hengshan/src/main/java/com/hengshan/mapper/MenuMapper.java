package com.hengshan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hengshan.entity.Menu;
import com.hengshan.entity.vo.MenuVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 权限表(Menu)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-22 15:42:58
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Results(id = "permsInfo", value = {
            @Result(column = "perms", property = "perms")
    })
    @Select("select distinct m.perms from user_role ur left join role_menu rm on ur.role_id=rm.role_id"
            + " left join menu m on rm.menu_id=m.id where ur.user_id=#{userId} and m.status='1'")
    List<String> selectPermsByUserId(@Param("userId") Long userId);

    @Select("select distinct m.id, m.name, m.type, m.parent_id, m.order_num, m.visible, m.status, " +
            "m.is_frame, m.icon, m.component, ifnull(m.perms,'') as perms, m.path " +
            "from user_role ur left join role_menu rm on ur.role_id=rm.role_id"
            + " left join menu m on rm.menu_id=m.id where ur.user_id=#{userId} and m.status='1' and m.type in ('c','m') order by m.parent_id,m.order_num")
    List<MenuVo> selectMenuByUserId(@Param("userId") Long userId);
}
