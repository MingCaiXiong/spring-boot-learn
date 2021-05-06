package top.xiongmingcai.mall.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class MallWebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        //排除静态文件

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        //linux服务器文件目录
        registry.addResourceHandler("/public/**").addResourceLocations("file:///Users/xmc/IdeaProjects/mall.xiongmingcai.top/src/main/resources/public/");
        registry.addResourceHandler("/static/**").addResourceLocations("file:///Users/xmc/IdeaProjects/mall.xiongmingcai.top/src/main/resources/static/images");

        super.addResourceHandlers(registry);
    }

}
