package top.xiongmingcai.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.xiongmingcai.mall.filter.AdminFilter;

import javax.servlet.Filter;

@Configuration
public class AdminFilterConfig {
    //之前定义的将被拦截的类
    @Bean
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    //将过滤器发到链路中
    @Bean(name = "AdminFilterConf")
    public FilterRegistrationBean<Filter> adminFilterConfig() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(adminFilter());
        filterFilterRegistrationBean.addUrlPatterns("/admin/category/*");
//        filterFilterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterFilterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterFilterRegistrationBean.setName("AdminFilterConf");
        return filterFilterRegistrationBean;
    }
}
