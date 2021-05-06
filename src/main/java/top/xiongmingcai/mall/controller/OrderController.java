package top.xiongmingcai.mall.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/order")
    public ApiRestResponse createOrder(@Valid @RequestBody CreateOrderReq createOrderReq, @ApiIgnore HttpSession session) {
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        String orderNum = orderService.createOrder(createOrderReq, loginUser.getId());
        return ApiRestResponse.success(orderNum);
    }
}
