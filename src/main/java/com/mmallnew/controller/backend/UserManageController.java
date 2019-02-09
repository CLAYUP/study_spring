package com.mmallnew.controller.backend;

import com.mmallnew.common.Const;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.User;
import com.mmallnew.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理用户
 *
 * @author Y.
 * @version V1.0
 * @date Created in 1959 2019/2/7
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登录
     *
     * @param username 用户
     * @param password 密码
     * @param session  会话
     * @return com.mmallnew.common.ServiceResponse<com.mmallnew.pojo.User>
     * @author Y.
     * @date 2230 2019/2/7
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {

        ServiceResponse<User> response = iUserService.login(username, password);

        if (response.isSuccess()) {
            User user = response.getData();
            if ((user.getRole() == Const.Role.ROLE_ADMIN)) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServiceResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
