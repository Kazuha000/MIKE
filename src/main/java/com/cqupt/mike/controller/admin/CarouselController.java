package com.cqupt.mike.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 轮播图控制
 */
@Controller
@RequestMapping("/admin")
public class CarouselController {
    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "mike_carousel");
        return "admin/mike_carousel";
    }
}
