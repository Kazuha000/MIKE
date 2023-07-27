package com.cqupt.mike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;


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


    @GetMapping("/complex")
    public String complex(ModelMap map) {
        map.put("title", "Thymeleaf 语法测试");
        map.put("testString", "Spring Boot 商城");
        map.put("bool", true);
        map.put("testArray", new Integer[]{2021, 2022, 2023, 2024});
        map.put("testList", Arrays.asList("Spring", "Spring Boot", "Thymeleaf", "MyBatis", "Java"));
        Map testMap = new HashMap();
        testMap.put("platform", "book");
        testMap.put("title", "Spring Boot 商城项目实战");
        testMap.put("author", "十三");
        map.put("testMap", testMap);
        map.put("testDate", new Date());
        return "test/complex";
    }


    //已经自动配置，因此可以直接通过 @Autowired 注入进来
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 新增一条记录
    @GetMapping("/insert")
    @ResponseBody
    public String insert(String type, String name) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
            return "参数异常";
        }
        jdbcTemplate.execute("insert into jdbc_test(`ds_type`,`ds_name`) value (\"" + type + "\",\"" + name + "\")");
        return "SQL执行完毕";
    }

    // 删除一条记录
    @GetMapping("/delete")
    @ResponseBody
    public String delete(int id) {
        if (id < 0) {
            return "参数异常";
        }
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from jdbc_test where ds_id = \"" + id + "\"");
        if (CollectionUtils.isEmpty(result)) {
            return "不存在该记录，删除失败";
        }
        jdbcTemplate.execute("delete from jdbc_test where ds_id=\"" + id + "\"");
        return "SQL执行完毕";
    }

    // 修改一条记录
    @GetMapping("/update")
    @ResponseBody
    public String update(int id, String type, String name) {
        if (id < 0 || StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
            return "参数异常";
        }
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from jdbc_test where ds_id = \"" + id + "\"");
        if (CollectionUtils.isEmpty(result)) {
            return "不存在该记录，无法修改";
        }
        jdbcTemplate.execute("update jdbc_test set ds_type=\"" + type + "\", ds_name= \"" + name + "\" where ds_id=\"" + id + "\"");
        return "SQL执行完毕";
    }

    // 查询所有记录
    @GetMapping("/queryAll")
    @ResponseBody
    public List<Map<String, Object>> queryAll() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from jdbc_test");
        return list;
    }
}
