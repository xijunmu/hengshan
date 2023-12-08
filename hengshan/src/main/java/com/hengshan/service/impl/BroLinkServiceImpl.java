package com.hengshan.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.mapper.BroLinkMapper;
import com.hengshan.entity.BroLink;
import com.hengshan.service.BroLinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 友链表(BroLink)表服务实现类
 *
 * @author muxijun
 * @since 2023-12-07 16:14:40
 */
@Service("broLinkService")
public class BroLinkServiceImpl extends ServiceImpl<BroLinkMapper, BroLink> implements BroLinkService {

    @Resource
    private BroLinkMapper broLinkMapper;

    @Override
    public void deleteByIds(String ids) {
        String[] idsArr = ids.split(",");
        if (idsArr.length > 0) {
            List<Long> idList = Stream.of(idsArr).map(Long::valueOf).collect(Collectors.toList());
            broLinkMapper.deleteBatchIds(idList);
        }
    }
}

