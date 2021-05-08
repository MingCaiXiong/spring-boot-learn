package top.xiongmingcai.mall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.OrderService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ApiRestResponse getAdminOrders(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @ApiIgnore HttpSession session) {
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        PageInfo orderVo = orderService.findAllOrdersForAdmin(loginUser.getId(), pageNum, pageSize);
        return ApiRestResponse.success(orderVo);
    }

    @PutMapping("/orders/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo, @ApiIgnore HttpSession session) {
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        orderService.delivered(loginUser.getId(), orderNo);
        return ApiRestResponse.success();
    }
}
