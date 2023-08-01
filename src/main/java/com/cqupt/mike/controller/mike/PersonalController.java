package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.common.Constants;
import com.cqupt.mike.service.StudentService;
import com.cqupt.mike.until.Result;
import com.cqupt.mike.until.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
//学生用户的登陆注册退出
@Controller
public class PersonalController {

    @Resource
    private StudentService studentService;

    /**
     * 登录页面跳转
     * @return
     */
    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mike/login";
    }

    /**
     * 注册页面跳转
     * @return
     */
    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mike/register";
    }

    /**
     * 找回密码界面跳转
     * @return
     */
    @GetMapping({"/findpassword","findpassword.html"})
    public String findpasswordPage() {return "mike/findpassword";}


    /**
     * 提交登陆
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {//判断用户名、密码、验证码是否为空
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";//获取session中验证码的值
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {//判断验证码是否正确
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
//        String loginResult = studentService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        String loginResult = studentService.login(loginName, password, httpSession);//登陆信息传入service层，判断用户名、密码是否匹配
        //若返回信息为登陆成功，则登陆成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    /**
     * 提交注册
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           HttpSession httpSession) {
        //判断用户名、密码、验证码是否为空
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(email)){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_EMAIL_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        //获取session中验证码的值
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        //判断验证码是否正确
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        //向service层传入注册信息，注册
        String registerResult = studentService.register(loginName, password);
        //若返回信息为登陆成功，则登陆成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    /**
     * 退出登陆
     * @param httpSession 缓存的用户登陆信息
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        //清空缓存信息
        httpSession.removeAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        return "mike/login";
    }

    /**
     * 找回密码
     * @param email
     * @return
     */
    @PostMapping("/findpassword")
    @ResponseBody
    public Result findpassword(@RequestParam("loginName") String loginName,
                               @RequestParam("email") String email,
                           @RequestParam("verifyCode") String verifyCode,
                           HttpSession httpSession) {
        //判断用户名、密码、验证码是否为空
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(email)){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_EMAIL_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        //获取session中验证码的值
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        //判断验证码是否正确
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        //向service层传入信息，找回密码
        String findpasswordResult = studentService.findpassword(loginName, email);
        //若返回信息为登陆成功，则登陆成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(findpasswordResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //找回密码失败
        return ResultGenerator.genFailResult(findpasswordResult);
    }
}
