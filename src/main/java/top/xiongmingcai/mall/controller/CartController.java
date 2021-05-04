package top.xiongmingcai.mall.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xiongmingcai.mall.common.ApiRestResponse;

//[7-2 用户过滤器开发-慕课网体系课](https://class.imooc.com/lesson/1414#mid=35840)
@RestController
@RequestMapping("/cart")
public class CartController {
    @PostMapping
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        return ApiRestResponse.success();
    }
}
