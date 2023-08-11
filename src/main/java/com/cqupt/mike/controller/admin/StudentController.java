package com.cqupt.mike.controller.admin;

import com.cqupt.mike.dao.StudentMapper;
import com.cqupt.mike.entity.Student;
import com.cqupt.mike.service.CarouselService;
import com.cqupt.mike.service.StudentService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/student")
    public String studentPage(HttpServletRequest request) {
        request.setAttribute("path", "student");
        return "admin/student";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult pageResult=studentService.getstudentPage(pageUtil);
        return ResultGenerator.genSuccessResult(pageResult);
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定 -1-已注销)
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1 && lockStatus != -1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (studentService.lockUsers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }





    @Resource
    StudentMapper studentMapper;

    // 查询所有学生
    @GetMapping("/student/queryAll")
    public List<Student> queryAll() {
        return studentMapper.findAllStudents();
    }

    // 新增一个学生
    @GetMapping("/student/insert")
    public Boolean insert(Integer accountNo,String stName,String password){
        if (accountNo==null||StringUtils.isEmpty(stName) || StringUtils.isEmpty(password)){
            return false;
        }
        Student student =new Student();
        student.setAccountNo(accountNo);
        student.setStName(stName);
        student.setPassword(password);
        return studentMapper.insertStudent(student) > 0;
    }

    // 修改一个学生
    @GetMapping("/student/update")
    public Boolean update(Integer stId,Integer accountNo,String stName,String password) {
        if (stId == null || stId < 1 || StringUtils.isEmpty(stName) || StringUtils.isEmpty(password)) {
            return false;
        }
        Student student =new Student();//实体封装
        student.setStId(stId);//主键
        student.setAccountNo(accountNo);//账号
        student.setStName(stName);//用户名
        student.setPassword(password);//密码
        return studentMapper.updStudent(student) > 0;
    }

    // 删除一个学生
    @GetMapping("/student/delete")
    public Boolean delete(Integer stId) {
        if (stId == null || stId < 1) {
            return false;
        }
        return studentMapper.delStudent(stId) > 0;
    }
}