package com.cqupt.mike.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @GetMapping({"","/","index","/index.html"})  //跳转后台管理主页
    public String index() {
        return "teacher/index";
    }
}
