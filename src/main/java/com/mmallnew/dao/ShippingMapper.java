package com.mmallnew.dao;

import com.mmallnew.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ：Y.
 * @version :V1.0
 * @date ：Created in 10:47 2019/2/10
 */
public interface ShippingMapper {

    /**
     * 删除
     *
     * @param id id
     * @return int
     * @author Y.
     * @date 20:05 2019/2/10
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 20:05 2019/2/10
     */
    int insert(Shipping record);

    /**
     * 选择性插入
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 20:05 2019/2/10
     */
    int insertSelective(Shipping record);

    /**
     * 根据id查询
     *
     * @param id id
     * @return sipping pojo
     * @author Y.
     * @date 20:06 2019/2/10
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * 选择性更新
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 20:07 2019/2/10
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * 根据主键更新
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 20:08 2019/2/10
     */
    int updateByPrimaryKey(Shipping record);

    /**
     * 更据ShippingId和UserId更新数据
     *
     * @param record pojo
     * @return int
     * @author Y.
     * @date 20:08 2019/2/10
     */
    int updateByShippingAndUserId(Shipping record);

    /**
     * 根据ShippingId和UserId查找数据
     *
     * @param userId 用户ID
     * @param id     ShippingId
     * @return Shipping
     * @author Y.
     * @date 20:09 2019/2/10
     */
    Shipping selectShippingByShippingIdAndUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    /**
     * 删除记录根据主键和用户ID，防止横向越权
     *
     * @param userId 用户ID
     * @param id     主键ID ShippingId
     * @return int
     * @author Y.
     * @date 20:11 2019/2/10
     */
    int deleteByPrimaryKeyAndUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    List<Shipping> selectByUserId(@Param("userId") Integer userId);
}