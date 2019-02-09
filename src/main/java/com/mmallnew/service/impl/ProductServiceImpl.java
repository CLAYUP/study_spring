package com.mmallnew.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmallnew.common.Const;
import com.mmallnew.common.ResponseCode;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.dao.CategoryMapper;
import com.mmallnew.dao.ProductMapper;
import com.mmallnew.pojo.Category;
import com.mmallnew.pojo.Product;
import com.mmallnew.service.ICategoryService;
import com.mmallnew.service.IProductService;
import com.mmallnew.util.PropertiesUtil;
import com.mmallnew.util.DateTimeUtil;
import com.mmallnew.vo.ProductDetailVo;
import com.mmallnew.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：Y.
 * @version :V1.0
 * @date ：Created in 15:06 2019/2/9
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public ServiceResponse<String> saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if (rowCount > 0) {
                    return ServiceResponse.createBySuccessMessage("更新产品成功");
                }
                return ServiceResponse.createByErrorMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServiceResponse.createBySuccessMessage("新增产品成功");
                }
                return ServiceResponse.createByErrorMessage("新增产品失败");
            }
        }
        return ServiceResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServiceResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServiceResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServiceResponse.createByErrorMessage("修改销售产品状态失败");
    }

    @Override
    public ServiceResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServiceResponse.createByErrorMessage("产品已经下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();

        productDetailVo.setId(product.getId());
        productDetailVo.setSubTitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImage(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getId());

        if (category == null) {
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

    @Override
    public ServiceResponse<PageInfo> getProductList(int pageIndex, int pageSize) {
        // startPage --start
        // 填充自己的查询逻辑
        //pageHelper --收尾
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> products = productMapper.selectList();
        ArrayList<ProductListVo> productListVos = new ArrayList<>();
        for (Product productItem : products) {
            productListVos.add(assembleProductListVo(productItem));
        }
        PageInfo pageResult = new PageInfo<>(products);
        pageResult.setList(productListVos);
        return ServiceResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setName(product.getName());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        return productListVo;
    }

    @Override
    public ServiceResponse<PageInfo> searchProduct(String productName, Integer productId, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = "%" + productName + "%";
        }
        List<Product> products = productMapper.selectByNameAndProductId(productName, productId);
        ArrayList<ProductListVo> productListVos = Lists.newArrayList();
        for (Product productItem : products) {
            productListVos.add(assembleProductListVo(productItem));
        }
        PageInfo pageResult = new PageInfo<>(products);
        pageResult.setList(productListVos);
        return ServiceResponse.createBySuccess(pageResult);
    }

    @Override
    public ServiceResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServiceResponse.createByErrorMessage("产品已经下架或删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServiceResponse.createByErrorMessage("产品已经下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServiceResponse<PageInfo> getProductByKeyWordCategory(String keyWord, Integer categoryId,
                                                                 int pageIndex, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyWord) && categoryId == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ArrayList<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyWord)) {
                PageHelper.startPage(pageIndex, pageSize);
                ArrayList<ProductListVo> productListVoArrayList = Lists.newArrayList();
                PageInfo<ProductListVo> pageInfo = new PageInfo<>(productListVoArrayList);
                return ServiceResponse.createBySuccess(pageInfo);
            }

            assert category != null;
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();

        }
        if (StringUtils.isNotBlank(keyWord)) {
            keyWord = "%" + keyWord + "%";
        }

        PageHelper.startPage(pageIndex, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
                System.out.println(Arrays.toString(orderByArray));
            }
        }
        List<Product> products = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyWord) ? null : keyWord,
                categoryIdList.size() == 0 ? null : categoryIdList);

        System.out.println(categoryIdList);
        System.out.println(products.size());

        ArrayList<ProductListVo> productListVos = Lists.newArrayList();
        for (Product productItem : products) {
            productListVos.add(assembleProductListVo(productItem));
        }
        PageInfo pageInfo = new PageInfo<>(productListVos);
        pageInfo.setList(productListVos);
        return ServiceResponse.createBySuccess(pageInfo);
    }
}
