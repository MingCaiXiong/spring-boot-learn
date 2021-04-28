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
    USER_CREATION_FAILED(10005, "用户入库失败");
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
