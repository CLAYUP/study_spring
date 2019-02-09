package com.mmallnew.common;

import com.google.common.collect.Sets;

import java.util.Set;

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

    static final String NULL = "null";

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }


    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public enum ProductStatusEnum {
        /**
         *
         */
        ON_SALE(1, "在线");
        private int code;
        private String value;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
