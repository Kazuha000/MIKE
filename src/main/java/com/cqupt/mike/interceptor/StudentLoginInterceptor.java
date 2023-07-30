package com.cqupt.mike.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.cqupt.mike.common.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;


/**
 * 学生主页身份验证拦截器
 */
@Component //添加@Component注解，使其注册到IOC容器中
public class StudentLoginInterceptor implements HandlerInterceptor {

    @Override //在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理。
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("进入拦截器...");
        if (null == request.getSession().getAttribute(Constants.MIKE_STUDENT_SESSION_KEY)) {//读取并判断了当前session对象中是否存在mikeStudent对象
            response.sendRedirect(request.getContextPath() + "/login");
            System.out.println("未登录，拦截成功...");
            return false;
        } else {
            System.out.println("已登录，放行...");
            return true;
        }
    }

    @Override//在业务处理器处理请求执行完成后，生成视图之前执行。
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override//在DispatcherServlet完全处理完请求后被调用，可用于清理资源等，返回处理（已经渲染了页面）
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
