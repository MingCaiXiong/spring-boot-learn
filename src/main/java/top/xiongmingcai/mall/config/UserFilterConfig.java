package top.xiongmingcai.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xiongmingcai.mall.filter.UserFilter;

@Configuration
public class UserFilterConfig {

    @Bean
    public FilterRegistrationBean<UserFilter> perfFilter() {
        FilterRegistrationBean<UserFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserFilter());
        registration.addUrlPatterns("/cart/*");
        registration.addUrlPatterns("/order/*");
        return registration;
    }
}
