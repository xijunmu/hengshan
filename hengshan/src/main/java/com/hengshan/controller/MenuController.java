package com.hengshan.controller;


import com.hengshan.entity.Menu;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.service.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 权限表(Menu)表控制层
 *
 * @author muxijun
 * @since 2023-12-22 15:42:58
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuServiceImpl menuService;

    @PostMapping({"/add"})
    public Menu add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @PostMapping("/update")
    public Menu updateById(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id");
        return menuService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Integer id) {
        return menuService.deleteById(id);
    }

    @PostMapping("/getList")
    public PageVo<Menu> getList(@RequestBody Map<String, Object> map) {
        int pageNum = (int) map.get("pageNum");
        int pageSize = (int) map.get("pageSize");
        return menuService.getList(pageNum, pageSize);
    }

    @GetMapping("getById/{id}")
    public Menu getById(@PathVariable("id") Integer id) {
        return menuService.getOneById(id);
    }
}

