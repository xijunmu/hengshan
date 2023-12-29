package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.Menu;
import com.hengshan.entity.vo.MenuVo;

import java.util.List;

/**
 * 权限表(Menu)表服务接口
 *
 * @author muxijun
 * @since 2023-12-22 15:42:58
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询对应权限
     *
     * @param userId 用户id
     * @return List<String> 权限列表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户id查询对应菜单树
     *
     * @param userId 用户id
     */
    List<MenuVo> selectMenuTreeByUserId(Long userId);
}

