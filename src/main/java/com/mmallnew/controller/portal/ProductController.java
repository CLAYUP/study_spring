package com.mmallnew.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.service.IProductService;
import com.mmallnew.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台商品类
 *
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 19:27 2019/2/9
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    /**
     * 查询商品信息
     *
     * @param productId id
     * @return com.mmallnew.common.ServiceResponse<com.mmallnew.vo.ProductDetailVo>
     * @author Y.
     * @date 23:03 2019/2/9
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServiceResponse<ProductDetailVo> detail(Integer productId) {

        return iProductService.getProductDetail(productId);
    }

    /**
     * 搜索商品，根据keyWord或者ID搜索，并分页排序
     *
     * @param keyWord    搜索关键字
     * @param categoryId id
     * @param pageIndex  页码
     * @param pageSize   页码数量
     * @param orderBy    排序依据
     * @return com.mmallnew.common.ServiceResponse<com.github.pagehelper.PageInfo>
     * @author Y.
     * @date 23:04 2019/2/9
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServiceResponse<PageInfo> list(@RequestParam(value = "keyWord", required = false) String keyWord,
                                          @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                          @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

        return iProductService.getProductByKeyWordCategory(keyWord, categoryId, pageIndex, pageSize, orderBy);
    }

}
