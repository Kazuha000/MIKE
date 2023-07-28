package com.cqupt.mike.interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台系统身份验证拦截器
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    //在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("进入拦截器...");
        String uri = request.getRequestURI();
        if (uri.startsWith("/admin") && null == request.getSession().getAttribute("loginUser")) {
            request.getSession().setAttribute("errorMsg", "请登录");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            System.out.println("未登录，拦截成功...");
            return false;
        } else {
            request.getSession().removeAttribute("errorMsg");
            System.out.println("已登录，放行...");
            return true;
        }
    }

    //在业务处理器处理请求执行完成后，生成视图之前执行。
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }


    //在DispatcherServlet完全处理完请求后被调用，可用于清理资源等，返回处理（已经渲染了页面）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
