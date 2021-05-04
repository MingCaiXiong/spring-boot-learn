package top.xiongmingcai.mall.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xiongmingcai.mall.common.ApiRestResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理统一异常的handler
 */
@RestControllerAdvice("top.xiongmingcai.mall")
//@ControllerAdvice
public class GlobalException {
    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public ApiRestResponse handleException(Exception e) {
        e.printStackTrace();
        logger.error("未知异常！原因是:", e);
        return ApiRestResponse.error(ExceptionEnum.SYSTEM_ABNORMAL.getCode(), e.getMessage());
    }

    @ExceptionHandler(BussinessException.class)
    public ApiRestResponse handleBussinessException(BussinessException ex) {
        logger.error("Default BussinessException: ", ex);
        return ApiRestResponse.error(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ApiRestResponse exceptionHandler(NullPointerException e) {
        logger.error("发生空指针异常！原因是:", e);
        return ApiRestResponse.error(ExceptionEnum.BODY_NOT_MATCH.getCode(), e.getMessage());
    }

    //MethodArgumentNotVaildException是固定的注释错误的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return handleBinding(e.getBindingResult());
    }

    private ApiRestResponse handleBinding(BindingResult result) {
        //@Valid 和 BindingResult 是一一对应的，如果有多个@Valid，那么每个@Valid后面跟着的BindingResult就是这个@Valid的验证结果，顺序不能乱

        List<String> list1 = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list1.add(message);
            }
        }
        if (list1.size() == 0) {
            return ApiRestResponse.error(ExceptionEnum.SYSTEM_ABNORMAL);
        }
        return ApiRestResponse.error(ExceptionEnum.NEED_MISSING_PARAMETERS.getCode(), list1.toString());
    }
}

