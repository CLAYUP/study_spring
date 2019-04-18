package com.mmallnew.dao;

import com.mmallnew.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库操作 dao
 *
 * @author ：Y.
 * @version : v1.0
 * @date ：Created in 19:59 2019/2/7
 */
public interface ProductMapper {

    /**`
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
     * @param record pojo
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int insert(Product record);

    /**
     * 插入数据
     *
     * @param record pojo，会判断该字段的值是否为空来决定是否插入数据
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int insertSelective(Product record);

    /**
     * 查询根据主键
     *
     * @param id 主键ID
     * @return Product
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 有选择的更新
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 23:16 2019/2/9
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 全部更新
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 23:16 2019/2/9
     */
    int updateByPrimaryKey(Product record);

    /**
     * 查询商品，根据ID sac 排序
     *
     * @return List<Product>
     * @author Y.
     * @date 23:18 2019/2/9
     */
    List<Product> selectList();

    /**
     * 更据商品的名称、ID查找
     *
     * @param productName 名称
     * @param productId   ID
     * @return List<Product>
     * @author Y.
     * @date 23:19 2019/2/9
     */
    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);

    /**
     * 更据商品的名称和分类ID查找
     *
     * @param productName    名称
     * @param categoryIdList 分类ID list
     * @return List<Product>
     * @author Y.
     * @date 23:20 2019/2/9
     */
    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);
}