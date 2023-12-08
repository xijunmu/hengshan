package com.hengshan.controller;


import com.hengshan.common.ResultBody;
import com.hengshan.entity.BroLink;
import com.hengshan.service.BroLinkService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 友链表(BroLink)表控制层
 *
 * @author muxijun
 * @since 2023-12-07 16:14:40
 */
@RestController
@RequestMapping("/broLink")
public class BroLinkController {

    @Resource
    private BroLinkService broLinkService;

    @RequestMapping("/getLinkList")
    public List<BroLink> getLinkList() {
        return broLinkService.list();
    }

    @RequestMapping("/deleteLinkById")
    public ResultBody deleteLinkById(@RequestBody Map<String,Object> map) {
        int id = 0;
        if(map.get("id") != null) {
            id = (int)map.get("id");
        }
        boolean result = broLinkService.removeById(id);
        if(result){
            return ResultBody.success("删除成功");
        } else {
            return ResultBody.fail(500,"删除失败");
        }
    }

    @RequestMapping("/addLink")
    public BroLink addLink(@RequestBody BroLink broLink) {
        broLinkService.saveOrUpdate(broLink);
        return broLink;
    }

}

