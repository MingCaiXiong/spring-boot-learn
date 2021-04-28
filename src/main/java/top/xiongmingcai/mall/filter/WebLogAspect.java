package top.xiongmingcai.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut(value = "execution(public * top.xiongmingcai.mall.controller.*.*(..)) ")
    public void webLog() {

    }

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer log = new StringBuffer();
        String classMethod = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
        log.append("\n  \033[1;5;37;44mURL| ");
        log.append(request.getRequestURL());
        log.append("Method| ");
        log.append(request.getMethod());
        log.append("Addr| ");
        log.append(request.getRemoteAddr());
        log.append("  [classMethod| ");
        log.append(classMethod);
        log.append("]  [referer| ");
        log.append(request.getHeader("referer"));
        log.append("]  [parameter| ");
        log.append(Arrays.toString(joinPoint.getArgs()));
        log.append("\033[0m COOL");

        logger.info(log.toString());
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        logger.info("\n \033[1;5;37;44m response : " + new ObjectMapper().writeValueAsString(res) + "\033[0m COOL");
    }
}
