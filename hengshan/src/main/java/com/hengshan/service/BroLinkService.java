package com.hengshan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hengshan.entity.BroLink;

/**
 * 友链表(BroLink)表服务接口
 *
 * @author muxijun
 * @since 2023-12-07 16:14:40
 */
public interface BroLinkService extends IService<BroLink> {

    /**
     * 根据ids删除多条数据
     *
     * @param ids id列表
     */
    void deleteByIds(String ids);

}

