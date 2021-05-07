package top.xiongmingcai.mall.common;

import com.google.common.collect.Sets;
import top.xiongmingcai.mall.exception.BussinessException;
import top.xiongmingcai.mall.exception.ExceptionEnum;

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

    /**
     * 0用户已取消，10未付款（初始状态），20已付款，30已发货，40交易完成
     */
    public enum OrderStatusEnum {
        CANCEL(0, "用户已取消"),
        UNPAID(10, "未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISH(40, "交易完成");

        private String value;
        private Integer code;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeof(Integer code) {
            for (OrderStatusEnum value : values()) {
                if (value.getCode() == code) {
                    return value;
                }
            }
            throw new BussinessException(ExceptionEnum.NOT_EMUM);
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
        int UNCHECKED = 0;
        int CHECKED = 1;
    }
}
