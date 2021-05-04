package top.xiongmingcai.mall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Constant {
    public static String SALT = "@ha%#,&^4*#";
    public static String loginUser = "loginUser";

    public interface ProductListOrderBy {
        Set<String> PRICE_ASE_DESC = Sets.newHashSet("price desc", "price asc");
    }

    public enum ProductStatusEnum {
        ON_SALE(0, "下架"), // 通过构造函数定义枚举值 0-下架，1-上架
        SALE(1, "在线"); // 通过构造函数定义枚举值

        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public interface selected {
        //是否已勾选：0代表未勾选，1代表已勾选
        int UNCHECKED = 0;  // 普通用户
        int CHECKED = 1;     // 管理员
    }
}
