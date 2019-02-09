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
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 21:35 2019/2/8
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageContoller {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
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

    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
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

    @RequestMapping(value = "get_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse getChildrentParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrentParallelCategory(categoryId);
        } else {
            return ServiceResponse.createByErrorMessage("需要管理员权限");
        }
    }

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
