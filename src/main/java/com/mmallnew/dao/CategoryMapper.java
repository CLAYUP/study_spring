package com.mmallnew.dao;

import com.mmallnew.pojo.Category;

import java.util.List;

/**
 * 数据库操作 dao
 *
 * @author ：Y.
 * @version : v1.0
 * @date ：Created in 19:59 2019/2/7
 */
public interface CategoryMapper {

    /**
     * 根据主键删除记录
     *
     * @param id 主键ID
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     *
     * @param record 对象
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int insert(Category record);

    /**
     * 插入数据
     *
     * @param record 会判断该字段的值是否为空来决定是否插入数据
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int insertSelective(Category record);

    /**
     * 查询根据主键
     *
     * @param id 主键ID
     * @return Category
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    Category selectByPrimaryKey(Integer id);

    /**
     * 更新数据
     *
     * @param record pojo，会判断字段是否为空
     * @return int
     * @author Y.
     * @date 23:12 2019/2/9
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * 全部更新
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 23:12 2019/2/9
     */
    int updateByPrimaryKey(Category record);

    /**
     * 查找 子节点
     *
     * @param parentId id
     * @return List<Category>
     * @author Y.
     * @date 23:13 2019/2/9
     */
    List<Category> selectCategoryChildrenByParentId(Integer parentId);

}