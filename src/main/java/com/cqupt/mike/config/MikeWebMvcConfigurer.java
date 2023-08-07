package com.cqupt.mike.config;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.interceptor.AdminLoginInterceptor;
import com.cqupt.mike.interceptor.StudentLoginInterceptor;
import com.cqupt.mike.interceptor.TeacherLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 */
@Configuration
public class MikeWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private TeacherLoginInterceptor teacherLoginInterceptor;
    @Autowired
    private StudentLoginInterceptor studentLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的URL路径（后台管理系统登录拦截）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/carousels/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");
        // 添加一个拦截器，拦截以/teacher为前缀的URL路径（后台管理系统登录拦截）
        registry.addInterceptor(teacherLoginInterceptor)
                .addPathPatterns("/teacher/**")
                .excludePathPatterns("/teacher/course/**")
                .excludePathPatterns("/teacher/login");
        // 商城页面登录拦截
        registry.addInterceptor(studentLoginInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/register")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .addPathPatterns("/goods/detail/**")
                .addPathPatterns("/shop-cart")
                .addPathPatterns("/shop-cart/**")
                .addPathPatterns("/saveOrder")
                .addPathPatterns("/orders")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/personal")
                .addPathPatterns("/personal/updateInfo")
                .addPathPatterns("/selectPayType")
                .addPathPatterns("/payPage");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/course-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}

