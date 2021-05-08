package top.xiongmingcai.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xiongmingcai.mall.filter.AdminFilter;

import javax.servlet.Filter;

@Configuration
public class AdminFilterConfig {

    //将过滤器发到链路中
    @Bean(name = "AdminFilterConf")
    public FilterRegistrationBean<Filter> adminFilterConfig() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AdminFilter());
        registration.addUrlPatterns("/admin/*");


        registration.setName("AdminFilterConf");
        return registration;
    }
}
