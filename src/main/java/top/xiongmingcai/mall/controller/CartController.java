package top.xiongmingcai.mall.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.model.dao.UserMapper;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.vo.CartVo;
import top.xiongmingcai.mall.service.CartService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static top.xiongmingcai.mall.common.Constant.loginUser;

//[7-2 用户过滤器开发-慕课网体系课](https://class.imooc.com/lesson/1414#mid=35840)
@ApiOperation("购物车管理")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @Resource
    private UserMapper userMapper;

    @PostMapping
    public ApiRestResponse createCart(@RequestParam Integer productId, @RequestParam Integer count, @ApiIgnore HttpSession session) {
        User user = (User) session.getAttribute(loginUser);
        List<CartVo> cartVoList = cartService.addCart(user.getId(), productId, count);
        return ApiRestResponse.success(cartVoList);
    }

    @GetMapping("/list")
    public ApiRestResponse list(@ApiIgnore HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<CartVo> cartVoList = cartService.list(loginUser.getId());

        return ApiRestResponse.success(cartVoList);
    }

    @GetMapping("/session")
    public ApiRestResponse session(@ApiIgnore HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");


        return ApiRestResponse.success(loginUser);
    }

    @PostMapping("/session")
    public ApiRestResponse postsession(@ApiIgnore HttpServletRequest request) {
        User user = userMapper.selectByPrimaryKey(2);


        request.getSession().setAttribute("loginUser", user);


        return ApiRestResponse.success(loginUser);
    }
}
