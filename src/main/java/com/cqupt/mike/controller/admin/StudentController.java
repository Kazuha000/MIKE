package com.cqupt.mike.controller.admin;

import com.cqupt.mike.dao.StudentMapper;
import com.cqupt.mike.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class StudentController {

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