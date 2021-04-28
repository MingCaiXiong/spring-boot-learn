package top.xiongmingcai.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping(value = "/user")
    @ResponseBody
    public User getPerson() {
        return userService.getUser();
    }
}

