package com.mmallnew.service;

import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.User;

/**
 * 用户服务类
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 14:27 2019/2/7
 */
public interface IUserService {

    /**
     * 登陆验证
     *
     * @param username 用户名
     * @param password 密码
     * @return ServiceResponse<User>
     * @author :Y.
     * @date :22:10 2019/2/7
     */
    ServiceResponse<User> login(String username, String password);

    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return ServiceResponse<User>
     * @author :Y.
     * @date :22:10 2019/2/7
     */
    ServiceResponse<String> register(User user);

    /**
     * 检查某些字段的value是否已经存在于数据库中
     *
     * @param str  value
     * @param type key
     * @return ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:10 2019/2/7
     */
    ServiceResponse<String> checkValid(String str, String type);

    /**
     * 查询用户找回密码功能所需的问题
     *
     * @param username 用户名
     * @return ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:10 2019/2/7
     */
    ServiceResponse<String> selectQuestion(String username);

    /**
     * 验证问题答案是否一致
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:10 2019/2/7
     */
    ServiceResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 修改密码，回答问题正确过后会返回一个token用于修改密码验证
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken 验证
     * @return : ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:16 2019/2/7
     */
    ServiceResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    /**
     * 直接重置密码，前提用户已经登陆
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user        当前用户对象
     * @return : ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:18 2019/2/7
     */
    ServiceResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户信息使用
     *
     * @param user 用户对象
     * @return ：ServiceResponse<String> 状态码
     * @author :Y.
     * @date :22:20 2019/2/7
     */
    ServiceResponse<User> updateInformation(User user);

    /**
     * 获取用户信息，根据用户的id
     *
     * @param userId 用户id
     * @return :
     * @author :Y.
     * @date :22:20 2019/2/7
     */
    ServiceResponse<User> getInformation(Integer userId);
}
