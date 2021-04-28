package top.xiongmingcai.mall.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xiongmingcai.mall.common.ApiRestResponse;

/**
 * 处理统一异常的handler
 */
@RestControllerAdvice
//@ControllerAdvice
public class GlobalException {
    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        logger.error("Default Exception: ", e);
        return ApiRestResponse.error(ExceptionEnum.SYSTEM_ABNORMAL);
    }

    @ExceptionHandler(BussinessException.class)
    public Object handleBussinessException(BussinessException ex) {
        logger.error("Default BussinessException: ", ex);
        return ApiRestResponse.error(ex.getCode(), ex.getMsg());
    }
}
