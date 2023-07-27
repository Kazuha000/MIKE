package com.cqupt.mike.controller.mike;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
public class IndexController {
    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {

        return "mike/index";
    }
}
