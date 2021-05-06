package top.xiongmingcai.mall.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping
    public ApiRestResponse createOrder(CreateOrderReq createOrderReq, @ApiIgnore HttpSession session) {
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        orderService.createOrder(createOrderReq, loginUser.getId());
        return null;
    }
}
