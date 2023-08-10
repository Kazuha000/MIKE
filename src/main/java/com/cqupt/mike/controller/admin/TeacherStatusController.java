package com.cqupt.mike.controller.admin;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.dao.TeacherMapper;
import com.cqupt.mike.entity.Carousel;
import com.cqupt.mike.entity.Teacher;
import com.cqupt.mike.service.TeacherService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class TeacherStatusController {

    @Resource
    TeacherMapper teacherMapper;
    @Resource
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public String teachersPage(HttpServletRequest request) {
        request.setAttribute("path", "teacher");
        return "admin/teacher";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/teachers/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(teacherService.getMikeTeachersPage(pageUtil));
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/teachers/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (teacherService.lockTeachers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }
    // 查询所有教师
    @GetMapping("/teachers/queryAll")
    public List<Teacher> queryAll() {
        return teacherMapper.findAllTeachers();
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/teachers/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Teacher teacher) {
        if (StringUtils.isEmpty(teacher.getName())
                || StringUtils.isEmpty(teacher.getAccountNo())
                || StringUtils.isEmpty(teacher.getPassword())
                || StringUtils.isEmpty(teacher.getSex())
                || StringUtils.isEmpty(teacher.getPhone())
                || StringUtils.isEmpty(teacher.getEmail())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = teacherService.saveTeacher(teacher);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/teachers/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Teacher teacher) {
        if (Objects.isNull(teacher.getId())
                ||StringUtils.isEmpty(teacher.getName())
                || StringUtils.isEmpty(teacher.getAccountNo())
                || StringUtils.isEmpty(teacher.getPassword())
                || StringUtils.isEmpty(teacher.getPhone())
                || StringUtils.isEmpty(teacher.getEmail()))  {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = teacherService.updateTeacher(teacher);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/teachers/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Teacher teacher = teacherService.getUserDetailById(id);
        if (teacher == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(teacher);
    }



//     //新增一个老师
//    @GetMapping("/teacher/insert")
//    public Boolean insert(Integer accountNo,String Name,String password){
//        if (accountNo==null|| StringUtils.isEmpty(Name) || StringUtils.isEmpty(password)){
//            return false;
//        }
//        Teacher teacher =new Teacher();//实体封装
//        teacher.setAccountNo(accountNo);
//        teacher.setName(Name);
//        teacher.setPassword(password);
//        return teacherMapper.insertTeacher(teacher) > 0;
//    }
//
//    // 修改一个老师
//    @GetMapping("/teacher/update")
//    public Boolean update(Integer Id,Integer accountNo,String Name,String password) {
//        if (Id == null || Id < 1 || StringUtils.isEmpty(Name) || StringUtils.isEmpty(password)) {
//            return false;
//        }
//        Teacher teacher =new Teacher();//实体封装
//        teacher.setId(Id);//主键
//        teacher.setAccountNo(accountNo);//账号
//        teacher.setName(Name);//用户名
//        teacher.setPassword(password);//密码
//        return teacherMapper.updTeacher(teacher) > 0;
//    }
//
//    // 删除一个老师
//    @GetMapping("/teacher/delete")
//    public Boolean delete(Integer Id) {
//        if (Id == null || Id < 1) {
//            return false;
//        }
//        return teacherMapper.delTeacher(Id) > 0;
//    }
}

