package top.xiongmingcai.mall.exception;

/**
 * 异常枚举
 */
public enum ExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED__LESS_THAN_8_BITS(10002, "密码不能少于8位"),
    NEED_PASSWORD(10003, "密码不能为空"),
    NEED_DO_NOT_ALLOW_DUPLICATE_NAMES(10004,
            "不允许重名, 注册失败"),
    USER_CREATION_FAILED(10005, "用户入库失败"),
    USER_WRONG_PASSWORD(10006, "密码错误"),
    USERNAME_DOES_NOT_EXIST(10006, "用户名不存在"),
    NEED_TOLOGIN(10007, "需要登录"),
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    NEED_UPDATE_FAILED(10008, "更新失败"),
    NEED_NO_ADMINISTRATOR_RIGHTS(10009, "无管理员权限"),
    NEED_MISSING_PARAMETERS(10010, "缺少分类参数"),
    NEED_MISSING_PARAMETERS_ID(10010, "缺少分类ID"),
    NEED__CATEGORY_ALREADY_EXISTS(10011, "分类已存在"),
    NEED__PRODUCT_ALREADY_EXISTS(10011, "商品已存在"),
    NEED_CATEGORY__DOES_NOT_EXIST(10011, "分类不存在,请传入正确ID"),
    NEED_NO_DATA_FOUND(10011, "未找到数据,请传入正确ID"),
    USER_CREATION_CATEGORY_FAILED(100012, "分类入库失败"),
    USER_PICTURE_ROAD_KING_DOES_NOT_EXIST(100012, "图片路劲不存在"),
    PICTURE_SAVE_DOES_NOT_EXIST(100012, "图片保存失败"),
    NEED___LOST_GOODS_IN_STORAGE(10011, "商品入库失败"),
    Not_ENOUGH(10011, "商品不足"),
    NOT_SALE(10011, "商品已下架"),
    INVENTORY_SHORTAGE(10011, "库存不足"),
    NOT_EMUM(10011, "未找到对应点枚举类"),
    USER_CATEGORY_DELETION_FAILED(100012, "分类删除失败"),
    CART_EMPTY(100012, "购物车没有已勾选商品需要支付"),
    ORDER_DOES_NOT_EXIST(100012, "购物车订单不存在"),

    SYSTEM_ABNORMAL(20000, "系统异常");
    private Integer code;
    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ExceptionEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
