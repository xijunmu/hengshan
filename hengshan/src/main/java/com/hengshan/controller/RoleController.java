package com.hengshan.controller;


import com.hengshan.common.annotation.SystemLog;
import com.hengshan.entity.Role;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色表(Role)表控制层
 *
 * @author muxijun
 * @since 2023-12-22 15:43:24
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping({"/add"})
    public Role add(@RequestBody Role role) {
        return roleService.add(role);
    }

    @PostMapping("/update")
    public Role updateById(@RequestBody Role role) {
        return roleService.update(role);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id");
        return roleService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Integer id) {
        return roleService.deleteById(id);
    }

    @PostMapping("/getList")
    public PageVo<Role> getList(@RequestBody Map<String, Object> map) {
        int pageNum = (int) map.get("pageNum");
        int pageSize = (int) map.get("pageSize");
        return roleService.getList(pageNum, pageSize);
    }

    @GetMapping("getById/{id}")
    public Role getById(@PathVariable("id") Integer id) {
        return roleService.getOneById(id);
    }

}

