package com.hengshan.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.hengshan.entity.BroLink;
 
/**
 * 友链表(BroLink)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-07 16:14:40
 */
@Mapper
public interface BroLinkMapper extends BaseMapper<BroLink> {

}
