package top.xiongmingcai.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
            return ApiRestResponse.error(ExceptionEnum.NEED__LESS_THAN_8_BITS);
        }

        User register = userService.register(username, password);

        return ApiRestResponse.success();

    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(String username, String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 8) {
            return ApiRestResponse.error(ExceptionEnum.NEED__LESS_THAN_8_BITS);
        }

        User loginUser = userService.login(username, password);
        boolean isAdminUser = userService.checkPermissions(loginUser);
        if (isAdminUser) {
            session.setAttribute(Constant.loginUser, loginUser);
            return ApiRestResponse.success(/*loginUser*/);
        } else {
            return ApiRestResponse.error(ExceptionEnum.NEED_NO_ADMINISTRATOR_RIGHTS);
        }
    }


    @PostMapping("/signature")
    @ResponseBody
    public ApiRestResponse updateSignature(HttpSession session, String signature) {

        User loginUser = (User) session.getAttribute(Constant.loginUser);
        if (loginUser == null) {
            return ApiRestResponse.error(ExceptionEnum.NEED_TOLOGIN);

        } else {
            User user = new User();
            user.setId(loginUser.getId());
            user.setPersonalizedSignature(signature);
            userService.updatePersonalizedSignatureByUser(user);
            return ApiRestResponse.success(user);
        }
    }
}

