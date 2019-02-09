package com.mmallnew.controller.backend;

import com.mmallnew.common.Const;
import com.mmallnew.common.ResponseCode;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.User;
import com.mmallnew.service.ICategoryService;
import com.mmallnew.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理品类
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 21:35 2019/2/8
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加品类
     *
     * @param session      会话
     * @param categoryName 分类名
     * @param parentId     父节点id
     * @return ServiceResponse<String>
     * @author :Y.
     * @date :22:30 2019/2/9
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 修改分类名称
     *
     * @param session      会话
     * @param categoryId   分类ID
     * @param categoryName 新的分类名
     * @return ServiceResponse<String>
     * @author :Y.
     * @date :22:32 2019/2/9
     */
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 获取当前一级的品类id
     *
     * @param session    会话
     * @param categoryId 品类id
     * @return :com.mmallnew.common.ServiceResponse 状态码或者获取到的品类
     * @author :Y.
     * @date :22:32 2019/2/9
     */
    @RequestMapping(value = "get_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse getChildrentParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

    /**
     * 获取当前品类下的所有的子品类节点
     *
     * @param session    会话
     * @param categoryId 类别ID
     * @return :com.mmallnew.common.ServiceResponse
     * @author :Y.
     * @date :22:35 2019/2/9
     */
    @RequestMapping(value = "get_category_and_deep_children_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            /*
            递归查询出所有的子节点和当前节点
             */
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }
}
