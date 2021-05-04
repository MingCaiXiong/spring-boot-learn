package top.xiongmingcai.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xiongmingcai.mall.filter.UserFilter;

import javax.servlet.Filter;

@Configuration
public class UserFilterConfig {
    //之前定义的将被拦截的类
    @Bean
    public UserFilter userFilter() {
        return new UserFilter();
    }

    //将过滤器发到链路中
    @Bean(name = "UserFilterConfig")
    public FilterRegistrationBean<Filter> adminFilterConfig() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(userFilter());
        filterFilterRegistrationBean.addUrlPatterns("/cart/*");
        filterFilterRegistrationBean.addUrlPatterns("/order/*");
        filterFilterRegistrationBean.setName("UserFilterConf");
        return filterFilterRegistrationBean;
    }
}
