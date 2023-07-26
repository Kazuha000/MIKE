package com.cqupt.mike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello,spring boot!";
    }


    @GetMapping("/thymeleaf")
    public String hello(HttpServletRequest request, @RequestParam(value = "description", required = false, defaultValue = "springboot-thymeleaf") String description) {
        request.setAttribute("description", description);
        return "test/thymeleaf";
    }

    @GetMapping("/attributes")
    public String attributes(ModelMap map) {
        // 更改 h1 内容
        map.put("title", "Thymeleaf 标签演示");
        // 更改 id、name、value
        map.put("th_id", "thymeleaf-input");
        map.put("th_name", "thymeleaf-input");
        map.put("th_value", "12");
        // 更改 class、href
        map.put("th_class", "thymeleaf-class");
        map.put("th_href", "http://13blog.site");
        return "test/attributes";
    }


    @GetMapping("/simple")                     //Thymeleaf简单语法实践,
    // 主要介绍字面量及简单的运算操作，包括字符串、数字、布尔值等常用的字面量及常用的运算和拼接操作
    public String simple(ModelMap map) {
        map.put("thymeleafText", "spring-boot");
        map.put("number1", 2021);
        map.put("number2", 2);
        return "test/simple";
    }
}
