package com.mmallnew.dao;

import com.mmallnew.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库操作 dao
 *
 * @author ：Y.
 * @version : v1.0
 * @date ：Created in 19:59 2019/2/7
 */
public interface UserMapper {

    /**
     * 根据主键删除记录
     *
     * @param id 主键ID
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据
     *
     * @param record 用户对象，会判断该字段的值是否为空来决定是否插入数据
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int insertSelective(User record);

    /**
     * 插入记录
     *
     * @param record 用户对象
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int insert(User record);

    /**
     * 查询根据主键
     *
     * @param id 主键ID
     * @return User
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 选择性更新字段
     *
     * @param record 主键ID
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 全部更新
     *
     * @param record 主键ID
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int updateByPrimaryKey(User record);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return int
     * @author :Y.
     * @date :22:40 2019/2/7
     */
    int checkUsername(String username);


    /**
     * 登录验证
     *
     * @param username    用户名
     * @param md5Password 加密后的密码
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    User selectLogin(@Param("username") String username, @Param("password") String md5Password);

    /**
     * 查询用户找回密码的问题
     *
     * @param username 用户名
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    String selectQuestionByUseranme(String username);

    /**
     * 检查邮箱
     *
     * @param str 查询条件
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int checkEmail(String str);

    /**
     * 验证问题答案是否一致，根据用户名
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 检查密码
     *
     * @param password 密码
     * @param userId   用户ID
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    /**
     * 检查邮箱
     *
     * @param email  邮箱
     * @param userId 用户id
     * @return :
     * @author :Y.
     * @date :22:47 2019/2/7
     */
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}