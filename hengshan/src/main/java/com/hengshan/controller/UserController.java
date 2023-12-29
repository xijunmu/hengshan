package com.hengshan.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.hengshan.common.annotation.SystemLog;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.common.utils.BeanCopyUtil;
import com.hengshan.common.utils.WebUtil;
import com.hengshan.entity.User;
import com.hengshan.entity.vo.ExcelUserVo;
import com.hengshan.entity.vo.PageVo;
import com.hengshan.entity.vo.UserInfoVo;
import com.hengshan.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表(User)表控制层
 *
 * @author muxijun
 * @since 2023-12-07 18:34:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping({"/add", "/register"})
    public UserInfoVo add(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/update")
    public UserInfoVo updateById(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Long> ids = (List<Long>) map.get("id");
        return userService.removeByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        return userService.removeById(id);
    }

    @PostMapping("/getList")
    public PageVo<UserInfoVo> getList(@RequestBody Map<String, Object> map) {
        int pageNum = (int) map.get("pageNum");
        int pageSize = (int) map.get("pageSize");
        String username = "";
        if (Objects.nonNull(map.get("username"))) {
            username = map.get("username").toString().trim();
        }
        return userService.getList(pageNum, pageSize, username);
    }

    @SystemLog(businessName = "获取用户信息")
    @GetMapping("getById/{id}")
    public UserInfoVo getById(@PathVariable("id") Long id) {
        return userService.getOneById(id);
    }

    @PreAuthorize("@permission.hasPermission('system:user:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            WebUtil.setDownLoadHeader("用户列表.xlsx", response);
            List<ExcelUserVo> excelUserVos = BeanCopyUtil.copyBeanList(userService.list(), ExcelUserVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelUserVo.class).autoCloseStream(Boolean.FALSE).sheet().doWrite(excelUserVos);
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.renderString(response, JSON.toJSONString(ReturnCode.SYSTEM_ERROR));
        }
    }
}

