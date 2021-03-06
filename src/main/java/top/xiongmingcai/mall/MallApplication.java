package top.xiongmingcai.mall;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//作用是扫描Mapper接口 在编译之后会生成相应的接口实现类
@MapperScan(basePackages = "top.xiongmingcai.mall.model.dao")
@EnableCaching
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

}
