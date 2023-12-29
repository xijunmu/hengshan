package com.hengshan.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hengshan.common.enums.ReturnCode;
import com.hengshan.entity.BroLink;
import com.hengshan.exception.SystemException;
import com.hengshan.mapper.BroLinkMapper;
import com.hengshan.service.BroLinkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Transactional
    public boolean deleteByIds(List<Integer> ids) {
        return removeBatchByIds(ids);
    }

    public boolean deleteById(Integer id) {
        return removeById(id);
    }

    public List<BroLink> getList() {
        return list();
    }

    public BroLink getOneById(Integer id) {
        return getById(id);
    }

    public BroLink add(BroLink broLink) {
        LambdaQueryWrapper<BroLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BroLink::getName, broLink.getName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(ReturnCode.ROLE_EXIST);
        }
        save(broLink);
        return getById(broLink.getId());
    }

    public BroLink update(BroLink broLink) {
        updateById(broLink);
        return broLink;
    }
}

