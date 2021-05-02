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
    NEED_TOLOGIN(10007, "需要登录"),
    NEED_UPDATE_FAILED(10008, "更新失败"),
    NEED_NO_ADMINISTRATOR_RIGHTS(10009, "无管理员权限"),
    NEED_MISSING_PARAMETERS(10010, "缺少分类参数"),
    NEED_MISSING_PARAMETERS_ID(10010, "缺少分类ID"),
    NEED__CATEGORY_ALREADY_EXISTS(10011, "分类已存在"),
    NEED_CATEGORY__DOES_NOT_EXIST(10011, "分类不存在,请传入正确ID"),
    USER_CREATION_CATEGORY_FAILED(100012, "分类入库失败"),

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
