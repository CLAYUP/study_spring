package com.mmallnew.common;

/**
 * 一些项目所需的常量的部署
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 15:31 2019/2/7
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public static final String NULL = "null";

    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }
}
