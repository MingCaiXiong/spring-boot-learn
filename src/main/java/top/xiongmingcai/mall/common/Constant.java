package top.xiongmingcai.mall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Constant {
    public static String SALT = "@ha%#,&^4*#";
    public static String loginUser = "loginUser";

    public interface ProductListOrderBy {
        Set<String> PRICE_ASE_DESC = Sets.newHashSet("price desc", "price asc");
    }
}
