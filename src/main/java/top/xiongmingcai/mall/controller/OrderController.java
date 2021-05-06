package top.xiongmingcai.mall.controller;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CreateOrderReq;
import top.xiongmingcai.mall.model.vo.OrderVo;
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

    @GetMapping("/order/{orderNo}")
    public ApiRestResponse getOrder(@PathVariable("orderNo") String orderNo, @ApiIgnore HttpSession session) {
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        OrderVo orderVo = orderService.orderInfo(orderNo, loginUser.getId());
        return ApiRestResponse.success(orderVo);
    }
}
