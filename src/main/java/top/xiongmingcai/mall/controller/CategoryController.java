package top.xiongmingcai.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CategoryReq;
import top.xiongmingcai.mall.service.CategoryService;
import top.xiongmingcai.mall.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ApiRestResponse addCategory(CategoryReq categoryReq, HttpSession session) {
        //请求参数效验
        if (categoryReq.getName() == null || categoryReq.getOrderNum() == null || categoryReq.getParentId() == null || categoryReq.getType() == null) {
            ApiRestResponse.error(ExceptionEnum.NEED_MISSING_PARAMETERS);
        }
        //登录状态效验
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        if (loginUser == null) {
            return ApiRestResponse.error(ExceptionEnum.NEED_TOLOGIN);

        }
        //管理员权限效验
        boolean admin = userService.checkPermissions(loginUser);
        if (!admin) {
            return ApiRestResponse.error(ExceptionEnum.NEED_NO_ADMINISTRATOR_RIGHTS);
        } else {
            Category category = categoryService.insertOneCategory(categoryReq);
            return ApiRestResponse.success(category);
        }
    }
}
