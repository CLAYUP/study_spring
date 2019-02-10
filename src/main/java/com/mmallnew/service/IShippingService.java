package com.mmallnew.service;

import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Shipping;

/**
 * 地址接口类
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 10:48 2019/2/10
 */
public interface IShippingService {

    /**
     * 新增收货地址
     *
     * @param userId   id
     * @param shipping pojo
     * @return ServiceResponse
     * @author Y.
     * @date 10:57 2019/2/10
     */
    ServiceResponse add(Integer userId, Shipping shipping);

    /**
     * 依据用户id和地址id删除收货地址
     *
     * @param userId     id
     * @param shippingId 地址id
     * @return ServiceResponse<String>
     * @author Y.
     * @date 11:06 2019/2/10
     */
    ServiceResponse<String> delete(Integer userId, Integer shippingId);

    /**
     * 更新地址
     *
     * @param userId   id
     * @param shipping pojo
     * @return ServiceResponse<String>
     * @author Y.
     * @date 19:05 2019/2/10
     */
    ServiceResponse<String> update(Integer userId, Shipping shipping);

    /**
     * 根据用户ID和地址编码查询用户信息
     *
     * @param userId     用户id
     * @param shippingId 地址编号
     * @return ServiceResponse
     * @author Y.
     * @date 19:19 2019/2/10
     */
    ServiceResponse shippingDetail(Integer userId, Integer shippingId);

    /**
     * 查询地址，分页
     *
     * @param userId    用户ID
     * @param pageIndex 页码
     * @param pageSize  页面数量 默认10
     * @return ServiceResponse
     * @author Y.
     * @date 19:26 2019/2/10
     */
    ServiceResponse list(Integer userId, int pageIndex, int pageSize);
}
