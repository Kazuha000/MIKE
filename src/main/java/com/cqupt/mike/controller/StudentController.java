package com.cqupt.mike.controller;

import com.cqupt.mike.dao.StudentMapper;
import com.cqupt.mike.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.List;

@RestController
public class StudentController {

    @Resource
    StudentMapper studentMapper;

    // 查询所有记录
    @GetMapping("/student/queryAll")
    public List<Student> queryAll() {
        return studentMapper.findAllStudents();
    }

    // 新增一条记录
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

    // 修改一条记录
    @GetMapping("/student/update")
    public Boolean update(Integer stId,Integer accountNo,String stName,String password) {
        if (stId == null || stId < 1 || StringUtils.isEmpty(stName) || StringUtils.isEmpty(password)) {
            return false;
        }
        Student student =new Student();
        student.setStId(stId);
        student.setAccountNo(accountNo);
        student.setStName(stName);
        student.setPassword(password);
        return studentMapper.updStudent(student) > 0;
    }

    // 删除一条记录
    @GetMapping("/student/delete")
    public Boolean delete(Integer stId) {
        if (stId == null || stId < 1) {
            return false;
        }
        return studentMapper.delStudent(stId) > 0;
    }
}