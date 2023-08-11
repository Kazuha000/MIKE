package com.cqupt.mike.interceptor;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.dao.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CartNumberInterceptor implements HandlerInterceptor {

    @Resource
    private CartItemMapper CartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //购物车中的数量会更改，但是在这些接口中并没有对session中的数据做修改，这里统一处理一下
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MIKE_STUDENT_SESSION_KEY)) {
            //如果当前为登陆状态，就查询数据库并设置购物车中的数量值
            MikeStudentVo UserVO = (MikeStudentVo) request.getSession().getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
            //设置购物车中的数量
            UserVO.setShopCartItemCount(CartItemMapper.selectCountByUserId(UserVO.getStId()));
            request.getSession().setAttribute(Constants.MIKE_STUDENT_SESSION_KEY, UserVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}