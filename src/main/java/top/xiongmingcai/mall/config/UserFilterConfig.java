package top.xiongmingcai.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xiongmingcai.mall.filter.UserFilter;

@Configuration
public class UserFilterConfig {


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserFilter());
        registration.addUrlPatterns("/cart/*");
//        registration.addInitParameter("exclusions", "api/demand/gettypelist");
        registration.setName("UserFilterConf");
        return registration;
    }
}
