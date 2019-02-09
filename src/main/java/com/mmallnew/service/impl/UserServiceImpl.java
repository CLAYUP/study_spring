package com.mmallnew.service.impl;

import com.mmallnew.common.Const;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.common.TokenCache;
import com.mmallnew.dao.UserMapper;
import com.mmallnew.pojo.User;
import com.mmallnew.service.IUserService;
import com.mmallnew.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 14:29 2019/2/7
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录md5
        String md5Password = MD5Util.md5Encodeutf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServiceResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
        ServiceResponse<String> validResponse = checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.md5Encodeutf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("注册失败");
        }
        return ServiceResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                if (userMapper.checkUsername(str) > 0) {
                    return ServiceResponse.createByErrorMessage("用户名已经存在");
                }
            } else if (Const.EMAIL.equals(type)) {
                if (userMapper.checkEmail(str) > 0) {
                    return ServiceResponse.createByErrorMessage("邮箱已经存在");
                }
            }
            return ServiceResponse.createBySuccessMessage("验证通过");
        } else {
            return ServiceResponse.createByErrorMessage("参数错误");
        }
    }

    @Override
    public ServiceResponse<String> selectQuestion(String username) {
        ServiceResponse<String> valid = checkValid(username, Const.USERNAME);
        if (valid.isSuccess()) {
            return ServiceResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUseranme(username);

        if (StringUtils.isNotBlank(question)) {
            return ServiceResponse.createBySuccess(question);
        }
        return ServiceResponse.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public ServiceResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServiceResponse.createBySuccess(forgetToken);
        }
        return ServiceResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServiceResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServiceResponse.createByErrorMessage("参数错误，token需要传递");
        }
        ServiceResponse<String> valid = checkValid(username, Const.USERNAME);
        if (valid.isSuccess()) {
            return ServiceResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);

        if (StringUtils.isBlank(token)) {
            return ServiceResponse.createByErrorMessage("token无效或过期");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5EncodeUtf8 = MD5Util.md5Encodeutf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5EncodeUtf8);
            if (rowCount > 0) {
                return ServiceResponse.createBySuccessMessage("修改成功");
            }
        } else {
            return ServiceResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServiceResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServiceResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        int resultCount = userMapper.checkPassword(MD5Util.md5Encodeutf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.md5Encodeutf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServiceResponse.createBySuccessMessage("密码更新成功");
        } else {
            return ServiceResponse.createByErrorMessage("密码跟新失败");
        }
    }

    @Override
    public ServiceResponse<User> updateInformation(User user) {
        int reslutCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (reslutCount > 0) {
            return ServiceResponse.createByErrorMessage("email已经存在，请更换email在进行修改");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServiceResponse.createBySuccess("更新信息成功", user);
        }
        return ServiceResponse.createByErrorMessage("更新信息失败");
    }

    @Override
    public ServiceResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServiceResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServiceResponse.createBySuccess(user);
    }

    @Override
    public ServiceResponse checkAdminRole(User user) {
        if (user != null && user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServiceResponse.createBySuccess();
        }
        return ServiceResponse.createByError();
    }

}
