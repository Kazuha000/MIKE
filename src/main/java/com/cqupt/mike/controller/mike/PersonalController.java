package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.common.Constants;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.entity.Student;
import com.cqupt.mike.service.StudentService;
import com.cqupt.mike.util.MailUtils;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;


/**
 * 学生用户的登陆注册退出
 */
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
    @GetMapping({"/forgetpassword","forgetpassword.html"})
    public String forgetpasswordPage() {return "mike/forgetpassword";}

    /**
     * 个人信息页面跳转
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mike/personal";
    }

    @GetMapping("/personal/email")
    public String emailsPage() {
        return "mike/email";
    }

    /**
     * 成功发送邮箱验证码后
     * @return
     */
    @GetMapping({"/comparevcode","comparevcode.html"})
    public  String comparevcodePage(){return "mike/comparevcode";}

    /**
     * 重置密码界面跳转
     * @return
     */
    @GetMapping({"/resetpassword","resetpassword.html"})
    public  String resetpasswordPage(){return "mike/resetpassword";}



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
        String registerResult = studentService.register(loginName, password,email);
        //若返回信息为登陆成功，则登陆成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody Student student, HttpSession httpSession) {
        MikeStudentVo mallUserTemp = studentService.updateUserInfo(student, httpSession);
        if (mallUserTemp == null) {
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        } else {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }


    /**
     * 忘记密码
     * @param email
     * @return
     */
    @PostMapping("/forgetpassword")
    @ResponseBody
    public Result forgetpassword(@RequestParam("loginName") String loginName,
                                 @RequestParam("email") String email,
                                 @RequestParam("verifyCode") String verifyCode,
                                 HttpSession httpSession,
                                 HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse) {
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

//        获取session中验证码的值
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        //判断验证码是否正确
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }

        //向service层传入信息，找回密码
        String forgetpasswordResult = studentService.forgetpassword(loginName, email,httpSession,httpServletRequest);
        //若返回信息为登陆成功，则发送验证码
        if (ServiceResultEnum.SUCCESS.getResult().equals(forgetpasswordResult)) {

            //服务器通知浏览器不要缓存
            httpServletResponse.setHeader("Cache-Control", "no-store");
            httpServletResponse.setHeader("Pragma", "no-cache");
            httpServletResponse.setDateHeader("Expires", 0);

            //生成随机数作为邮箱验证码
            String base = "0123456789ABCDEFGHIJKLMNOPQRSDUVWXYZabcdefghijklmnopqrsduvwxyz";
            int size = base.length();
            Random r = new Random();
            StringBuilder code = new StringBuilder();
            for(int i=1;i<=4;i++){
                //产生0到size-1的随机值
                int index = r.nextInt(size);
                //在base字符串中获取下标为index的字符
                char c = base.charAt(index);
                //将c放入到StringBuffer中去
                code.append(c);
            }

            // 邮箱验证码存入session
            httpServletRequest.getSession().setAttribute(Constants.VERIFY_EMAIL_KEY,code.toString());
            try {
                MailUtils.sendMail(email,code.toString());
            } catch (MessagingException e) {
                throw new RuntimeException(e.getMessage());
            }
            return ResultGenerator.genSuccessResult();
        }
        //找回密码失败
        return ResultGenerator.genFailResult(forgetpasswordResult);
    }

    /**
     * 比对验证码
     *
     * @param verifyCode
     * @param httpSession
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */

    @PostMapping("/comparevcode")
    @ResponseBody
    public Result comparevcode(
            @RequestParam("verifyCode") String verifyCode,
            HttpSession httpSession,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        //判断验证码是否为空
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
//        获取session中验证码的值
        String vCode = httpSession.getAttribute(Constants.VERIFY_EMAIL_KEY) + "";
        //判断验证码是否正确
        if (!verifyCode.equals(vCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        //比对验证码成功
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 重置密码
     *
     * @param newpassword
     * @param httpSession
     * @return
     */
    @PostMapping("/resetpassword")
    @ResponseBody
    public Result resetpassword(
                           @RequestParam("password1") String newpassword,
                           HttpSession httpSession,
                           HttpServletRequest httpServletRequest) {
        //判断密码是否为空
        if (StringUtils.isEmpty(newpassword)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        //向service层传入注册信息，注册
        String n= httpServletRequest.getSession().getAttribute("stId").toString();
        int id = Integer.parseInt(n);
        String registerResult = studentService.resetpassword(id, newpassword);
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


}
