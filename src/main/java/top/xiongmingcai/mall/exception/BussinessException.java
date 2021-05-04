package top.xiongmingcai.mall.exception;

/**
 * 业务相关异常类
 */
public class BussinessException extends RuntimeException {
    private Integer code;
    private String msg;


    public BussinessException() {
    }

    public BussinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BussinessException(ExceptionEnum ex) {
        super(ex.getMsg());
        this.code = ex.getCode();
        this.msg = ex.getMsg();
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
