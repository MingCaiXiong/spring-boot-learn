package top.xiongmingcai.mall.service.impl;

import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.xiongmingcai.mall.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImpTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void listForAdmin() {
        PageInfo pageInfo = categoryService.listForAdmin(1, 10);
        System.out.println("pageInfo = " + pageInfo);
    }
}