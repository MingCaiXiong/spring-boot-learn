package top.xiongmingcai.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping(value = "/test")
    @ResponseBody
    public User getPerson() {
        return userService.getUser();
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse response(String username, String password) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 8) {
            return ApiRestResponse.error(1004, "密码不能少于8位");
        }

        return ApiRestResponse.success();
    }
}

