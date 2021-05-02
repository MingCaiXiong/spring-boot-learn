package top.xiongmingcai.mall.filter;

import top.xiongmingcai.mall.common.Constant;
import top.xiongmingcai.mall.model.pojo.User;
import top.xiongmingcai.mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Resource
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=utf-8");

        HttpSession session = request.getSession();
        //登录状态效验
        User loginUser = (User) session.getAttribute(Constant.loginUser);
        if (loginUser == null) {
            response.getWriter().write("{\n" +
                    "    \"code\": 1007,\n" +
                    "    \"msg\": \"用户未登录\",\n" +
                    "    \"data\": null\n" +
                    "}");
            response.getWriter().flush();
            //关闭
            response.getWriter().close();
            //不会进入controller层
            return;
        }
        //管理员权限效验
        boolean admin = userService.checkPermissions(loginUser);
        if (!admin) {
            response.getWriter().write("{\n" +
                    "    \"code\": 1007,\n" +
                    "    \"msg\": \"没有操作权限\",\n" +
                    "    \"data\": null\n" +
                    "}");
            response.getWriter().flush();
            //关闭
            response.getWriter().close();
            //不会进入controller层
            return;

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
