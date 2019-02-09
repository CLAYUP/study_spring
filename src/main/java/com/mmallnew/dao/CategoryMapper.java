package com.mmallnew.dao;

import com.mmallnew.pojo.Category;

import java.util.List;

/**
 * @author Yang
 */
public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectCategoryChildrenByParentId(Integer parentId);

}