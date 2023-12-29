package com.hengshan.controller;


import com.hengshan.entity.BroLink;
import com.hengshan.service.impl.BroLinkServiceImpl;
import org.springframework.web.bind.annotation.*;

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
    private BroLinkServiceImpl broLinkService;

    @PostMapping("/getList")
    public List<BroLink> getList() {
        return broLinkService.getList();
    }

    @GetMapping("getById/{id}")
    public BroLink getById(@PathVariable("id") Integer id) {
        return broLinkService.getOneById(id);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id");
        return broLinkService.deleteByIds(ids);
    }

    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable("id") Integer id) {
        return broLinkService.deleteById(id);
    }

    @PostMapping("/add")
    public BroLink add(@RequestBody BroLink broLink) {
        return broLinkService.add(broLink);
    }

    @PostMapping("/update")
    public BroLink updateById(@RequestBody BroLink broLink) {
        return broLinkService.update(broLink);
    }
}

