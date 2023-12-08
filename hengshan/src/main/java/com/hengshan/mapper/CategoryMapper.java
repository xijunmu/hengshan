package com.hengshan.mapper;
 
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.hengshan.entity.Category;
 
/**
 * 文章分类表(Category)表数据库访问层
 *
 * @author muxijun
 * @since 2023-12-06 14:51:14
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
