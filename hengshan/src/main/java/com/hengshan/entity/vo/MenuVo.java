package com.hengshan.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {

    //ID
    private Integer id;

    //菜单名称
    private String name;

    //权限类型：m目录 c菜单 b按钮
    private String type;

    //父级ID：为0时且type为M则是一级菜单
    private Integer parentId;

    //显示顺序
    private Integer orderNum;

    //菜单显示状态：0隐藏 1显示
    private String visible;

    //菜单状态：0停用 1正常
    private String status;

    //是否为外链：0否 1是
    private String isFrame;

    //菜单图标
    private String icon;

    //菜单组件
    private String component;

    //路由地址
    private String path;

    private List<MenuVo> children;
}
