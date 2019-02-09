package com.mmallnew.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmallnew.common.Const;
import com.mmallnew.common.ResponseCode;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Product;
import com.mmallnew.pojo.User;
import com.mmallnew.service.IFileService;
import com.mmallnew.service.IProductService;
import com.mmallnew.service.IUserService;
import com.mmallnew.util.PropertiesUtil;
import com.mmallnew.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 后台管理商品类
 *
 * @author ：Y.
 * @version V1.0
 * @date ：Created in 1502 2019/2/9
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    /**
     * 新增商品
     *
     * @param session 回话
     * @param product 商品
     * @return com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author Y.
     * @date 2247 2019/2/9
     */
    @RequestMapping(value = "product_save.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 修改商品状态
     *
     * @param session   会话
     * @param productId 产品ID
     * @param status    状态
     * @return com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author Y.
     * @date 2248 2019/2/9
     */
    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 获取商品信息
     *
     * @param session   会话
     * @param productId ID
     * @return com.mmallnew.common.ServiceResponse<com.mmallnew.vo.ProductDetailVo>
     * @author Y.
     * @date 22:50 2019/2/9
     */
    @RequestMapping(value = "get_detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<ProductDetailVo> getDetail(HttpSession session, Integer productId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 查询商品并且分页
     *
     * @param session   会话
     * @param pageIndex 页码
     * @param pageSize  每页数量
     * @return com.mmallnew.common.ServiceResponse<com.github.pagehelper.PageInfo>
     * @author Y.
     * @date 22:51 2019/2/9
     */
    @RequestMapping(value = "get_list.do")
    @ResponseBody
    public ServiceResponse<PageInfo> getList(HttpSession session,
                                             @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageIndex, pageSize);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 搜索商品并且分页，查询字段为商品名字和ID，如果字段不为空就搜索，反之不搜索
     *
     * @param session     会话
     * @param productName 搜索的关键字
     * @param productId   搜索的ID
     * @param pageIndex   页码
     * @param pageSize    每页数量
     * @return com.mmallnew.common.ServiceResponse<com.github.pagehelper.PageInfo>
     * @author Y.
     * @date 22:53 2019/2/9
     */
    @RequestMapping(value = "product_search.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<PageInfo> productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.searchProduct(productName, productId, pageIndex, pageSize);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 上传文件
     *
     * @param session 会话
     * @param file    文件对象
     * @param request 会话，用来获取文件路径
     * @return com.mmallnew.common.ServiceResponse<java.util.HashMap               <               java.lang.Object                                                               ,                                                               java.lang.Object>>
     * @author Y.
     * @date 22:58 2019/2/9
     */
    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<HashMap<Object, Object>> upload(HttpSession session, MultipartFile file, HttpServletRequest request) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("") + targetFileName;
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("uri", targetFileName);
            map.put("url", url);
            return ServiceResponse.createBySuccess(map);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 富文本编辑器所带的文件上传
     *
     * @param session  回话
     * @param file     文件对象
     * @param request  请求
     * @param response 响应
     * @return java.util.HashMap<java.lang.Object   ,   java.lang.Object>
     * @author Y.
     * @date 23:00 2019/2/9
     */
    @RequestMapping(value = "richtext_img_upload.do", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<Object, Object> richtextImgUpload(HttpSession session, MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        HashMap<Object, Object> map = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            map.put("success", false);
            map.put("msg", "请登录管理员");
            return map;
        }
        // 富文本对上传文件有要求
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                map.put("success", false);
                map.put("msg", "上传失败");
                return map;
            }
            String url = PropertiesUtil.getProperty("") + targetFileName;
            map.put("success", true);
            map.put("msg", "上传失败");
            map.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return map;
        } else {
            map.put("success", false);
            map.put("msg", "需要管理员权限");
            return map;
        }
    }


}
