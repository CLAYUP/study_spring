package com.mmallnew.controller.portal;

import com.mmallnew.common.Const;
import com.mmallnew.common.ResponseCode;
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
 * 用户controller层
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 14:22 2019/2/7
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @param session  会话
     * @return ServiceResponse<User>
     * @author :Y.
     * @date :15:51 2019/2/7
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        ServiceResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 登出
     *
     * @param session 会话
     * @return :com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author :Y.
     * @date :22:31 2019/2/7
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session) {

        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createBySuccess();
    }

    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return :com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author :Y.
     * @date :22:31 2019/2/7
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 检查字段（type）对应的value（str）是否存在
     *
     * @param str  value
     * @param type 字段
     * @return :com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author :Y.
     * @date :22:32 2019/2/7
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     *
     * @param session 会话
     * @return :com.mmallnew.common.ServiceResponse<com.mmallnew.pojo.User>
     * @author :Y.
     * @date :22:34 2019/2/7
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user != null) {
            return ServiceResponse.createBySuccess(user);
        }
        return ServiceResponse.createByErrorMessage("用户未登陆，无法获取用户信息");
    }

    /**
     * 获取找回密码所需的问题
     *
     * @param username 用户名
     * @return :com.mmallnew.common.ServiceResponse
     * @author :Y.
     * @date :22:35 2019/2/7
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 验证问题的答案是否一致
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   用户的答案
     * @return :com.mmallnew.common.ServiceResponse
     * @author :Y.
     * @date :22:35 2019/2/7
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 问题验证通过后就可以修改密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken 验证
     * @return :com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author :Y.
     * @date :22:36 2019/2/7
     */
    @RequestMapping(value = "forget_rest_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetRestPassword(username, passwordNew, forgetToken);
    }

    /**
     * 直接重置用户密码
     *
     * @param session     会话
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @return :com.mmallnew.common.ServiceResponse<java.lang.String>
     * @author :Y.
     * @date :22:37 2019/2/7
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorMessage("请登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    /**
     * 更新用户信息
     *
     * @param session 会话
     * @param user    用户对象
     * @return :com.mmallnew.common.ServiceResponse<com.mmallnew.pojo.User>
     * @author :Y.
     * @date :22:39 2019/2/7
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> updateInformation(HttpSession session, User user) {

        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServiceResponse.createByErrorMessage("用户未登陆");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServiceResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
            return ServiceResponse.createBySuccessMessage("更新成功");
        }
        return response;
    }

    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
