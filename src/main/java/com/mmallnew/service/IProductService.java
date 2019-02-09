package com.mmallnew.service;

import com.github.pagehelper.PageInfo;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Product;
import com.mmallnew.vo.ProductDetailVo;

/**
 *
 * @author ：Y.
 * @version :V1.0
 * @date ：Created in 15:06 2019/2/9
 */
public interface IProductService {

    /**
     * 增加新的商品
     *
     * @param product pojo
     * @return ServiceResponse<String>
     * @author Y.
     * @date 23:27 2019/2/9
     */
    ServiceResponse<String> saveOrUpdateProduct(Product product);

    /**
     * 修改商品状态
     *
     * @param productId id
     * @param status    状态码
     * @return ServiceResponse<String>
     * @author Y.
     * @date 23:27 2019/2/9
     */
    ServiceResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 管理商品信息
     *
     * @param productId id
     * @return ServiceResponse<ProductDetailVo>
     * @author Y.
     * @date 23:28 2019/2/9
     */
    ServiceResponse<ProductDetailVo> manageProductDetail(Integer productId);

    /**
     * 获取商品列表，分页
     *
     * @param pageIndex 页码
     * @param pageSize  大小
     * @return ServiceResponse<PageInfo>
     * @author Y.
     * @date 23:29 2019/2/9
     */
    ServiceResponse<PageInfo> getProductList(int pageIndex, int pageSize);

    /**
     * 搜索商品，分页
     *
     * @param productName 名称
     * @param productId   ID
     * @param pageIndex   页码
     * @param pageSize    页面大小
     * @return ServiceResponse<PageInfo>
     * @author Y.
     * @date 23:30 2019/2/9
     */
    ServiceResponse<PageInfo> searchProduct(String productName, Integer productId, int pageIndex, int pageSize);

    /**
     * 依据ID获取商品信息
     *
     * @param productId id
     * @return ServiceResponse<ProductDetailVo>
     * @author Y.
     * @date 23:31 2019/2/9
     */
    ServiceResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 搜索商品依据关键字或者品类id
     *
     * @param keyWord    搜索关键字
     * @param categoryId id
     * @param pageIndex  页码
     * @param pageSize   页面大小
     * @param orderBy    排序字段
     * @return ServiceResponse<PageInfo>
     * @author Y.
     * @date 23:32 2019/2/9
     */
    ServiceResponse<PageInfo> getProductByKeyWordCategory(String keyWord, Integer categoryId, int pageIndex, int pageSize, String orderBy);
}
