package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.entity.Menu;
import com.hengshan.entity.vo.MenuVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.MenuMapper;
import com.hengshan.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限表(Menu)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-22 15:42:58
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Transactional
    public boolean deleteByIds(List<Integer> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    public PageVo<Menu> getList(int pageNum, int pageSize) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        page(page, null);
        return new PageVo<>(page.getRecords(), page.getTotal());
    }

    public Menu getOneById(Integer id) {
        return getById(id);
    }

    public Menu add(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getName, menu.getName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(ReturnCode.MENU_EXIST);
        }
        save(menu);
        return getById(menu.getId());
    }

    public Menu update(Menu menu) {
        updateById(menu);
        return menu;
    }

    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //超级管理员返回所有权限
        if (userId.equals(1L)) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getStatus, '1');
            List<Menu> menus = list(wrapper);
            return menus.stream().map(Menu::getPerms).collect(Collectors.toList());
        }
        return menuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<MenuVo> selectMenuTreeByUserId(Long userId) {
        List<MenuVo> menus;
        if (userId.equals(1L)) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getType, 'c', 'm');
            menus = BeanCopyUtil.copyBeanList(list(wrapper), MenuVo.class);
        } else {
            menus = menuMapper.selectMenuByUserId(userId);
        }
        return builderMenuTree(menus);
    }

    public List<MenuVo> builderMenuTree(List<MenuVo> menus) {
        List<MenuVo> tree = menus.stream().filter(menu -> menu.getParentId().equals(0)).collect(Collectors.toList());
        for (MenuVo menuVo : tree) {
            List<MenuVo> children = getChildren(menus, menuVo);
            menuVo.setChildren(children);
        }
        return tree;
    }

    /**
     * 递归获取顶层菜单的子菜单
     */
    public List<MenuVo> getChildren(List<MenuVo> menus, MenuVo menu) {
        List<MenuVo> children = menus.stream().filter(m -> m.getParentId().equals(menu.getId())).collect(Collectors.toList());
        for (MenuVo menuVo : children) {
            menuVo.setChildren(getChildren(menus, menuVo));
        }
        return children;
    }
}

