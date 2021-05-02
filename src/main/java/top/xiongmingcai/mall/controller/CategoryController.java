package top.xiongmingcai.mall.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.xiongmingcai.mall.common.ApiRestResponse;
import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.exception.ExceptionEnum;
import top.xiongmingcai.mall.model.pojo.Category;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.model.request.CategoryReq;
import top.xiongmingcai.mall.service.CategoryService;
import top.xiongmingcai.mall.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@ApiOperation("后台添加商品分类")
@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{categoryId}")
    public ApiRestResponse getCategory(@PathVariable("categoryId") Integer categoryId) {
        Category category = categoryService.findOneCategory(categoryId);
        return ApiRestResponse.success(category);
    }

    @PostMapping("/category")
    public ApiRestResponse addCategory(@Valid @RequestBody CategoryReq categoryReq, HttpSession session, BindingResult result) {
        //请求参数效验

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

    @PutMapping("/category")
    public ApiRestResponse updateCategory(@Valid CategoryReq categoryReq, HttpSession session) {
        //请求参数效验

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
            Category category = categoryService.updateOneCategory(categoryReq);
            return ApiRestResponse.success(category);
        }
    }
}
