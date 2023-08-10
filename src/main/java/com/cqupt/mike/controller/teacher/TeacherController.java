package com.cqupt.mike.controller.teacher;

import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.entity.Teacher;
import com.cqupt.mike.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @GetMapping({"","/","index","/index.html"})  //跳转后台管理主页
    public String index() {
        return "teacher/index";
    }

    @GetMapping({"/login"})  //管理员登陆跳转
    public String login() {
        return "teacher/login";
    }

    @PostMapping(value = "/login")    //管理员登陆
    public String login(@RequestParam("adName") String adName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {  //验证码为空则返回
            session.setAttribute("errorMsg", "验证码不能为空");
            return "teacher/login";
        }
        if (StringUtils.isEmpty(adName) || StringUtils.isEmpty(password)){ //用户名或密码为空则返回
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "teacher/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        //验证码错误则返回
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "teacher/login";
        }
        int loginResult = teacherService.login(adName,password);  //根据用户名从数据库查询对应的管理员用户，并返回用户id
        if (loginResult>0) { //id大于0，表示用户存在
            session.setAttribute("loginUser", adName);//传入登陆管理员的用户名到loginUser
            session.setAttribute("loginUserId",loginResult); //传入登陆用户的id到loginUserId
            //session过期时间设置为7200秒，即2小时
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/teacher/index";
        }else if(loginResult==0){//返回值为0表示密码错误
            session.setAttribute("errorMsg", "密码错误");
            return "teacher/login";
        }
        else if (loginResult==-1){//返回值为-1表示用户不存在
            session.setAttribute("errorMsg", "用户不存在");
            return "teacher/login";
        }
        session.setAttribute("errorMsg", "登录失败"); //其他登陆未成功的情况
        return "teacher/login";
    }

    @GetMapping("/profile")  //跳转到个人信息页面
    public String profile(HttpServletRequest request) {
        Integer adId = (int) request.getSession().getAttribute("loginUserId"); //从前端页面获取登陆用户id
        Teacher teacher = teacherService.getUserDetailById(adId);//通过id查询管理员用户
        if (teacher == null) {  //未查询到用户，返回登陆界面
            return "teacher/login";
        }
//        request.setAttribute("loginUser",..)与teacher.js里的th:value="${loginUser}"相对应
        request.setAttribute("path", "profile"); //传入个人信息页面路径
        request.setAttribute("loginUser", teacher.getName()); //传入登陆的教师用户名
        request.setAttribute("accountNo", teacher.getAccountNo());//传入教师账号
        request.setAttribute("phone", teacher.getPhone()); //传入教师手机号
        request.setAttribute("email", teacher.getEmail());//传入教师邮箱
        return "teacher/profile";
    }

    @PostMapping("/profile/password")  //修改密码
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        //判断旧密码和新密码是否为空
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        //获取登陆用户的id
        Integer adId = (int) request.getSession().getAttribute("loginUserId");
        if (teacherService.updatePassword(adId, originalPassword, newPassword)) {
            //修改成功后清空Session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/profile/name") //修改用户名和账号
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUser") String loginUser,
                             @RequestParam("accountNo") Integer accountNo, @RequestParam("phone") String phone,
                             @RequestParam("email") String email) {
        //判断输入的用户名和账号是否为空
        if (StringUtils.isEmpty(loginUser) || accountNo==null || phone==null || StringUtils.isEmpty(email)) {
            return "参数不能为空";
        }
        //从前端获取登陆的管理员用户的id
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        //修改用户名和账号
        if (teacherService.updateName(loginUserId, loginUser, accountNo,phone ,email)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //清空Session中的数据，前端控制跳转至登录页
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "teacher/login";
    }
}
