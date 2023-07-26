package com.cqupt.mike.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello,spring boot!";
    }
}
