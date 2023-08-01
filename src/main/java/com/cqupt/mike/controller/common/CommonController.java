package com.cqupt.mike.controller.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.cqupt.mike.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;


@Controller
public class CommonController {

    @GetMapping("/common/kaptcha")   //生成并输出验证码
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");


        // 三个参数分别为宽、高、位数
        SpecCaptcha captcha = new SpecCaptcha(150, 40, 4);

        // 设置类型 数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        //设置字体
        captcha.setCharType(Captcha.FONT_9);

        // 验证码存入session
        httpServletRequest.getSession().setAttribute("verifyCode", captcha.text().toLowerCase());

        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

    @GetMapping("/common/mike/kaptcha")
    public void mallKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");


        // 三个参数分别为宽、高、位数
        SpecCaptcha captcha = new SpecCaptcha(110, 40, 4);

        // 设置类型 数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        //设置字体
        captcha.setCharType(Captcha.FONT_9);

        // 验证码存入session
        httpServletRequest.getSession().setAttribute(Constants.VERIFY_CODE_KEY, captcha.text().toLowerCase());

        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

    @GetMapping("/common/kaptcha")   //生成并输出验证码
    public void mailKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //服务器通知浏览器不要缓存
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
//        httpServletResponse.setContentType("text/html");

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

        // 验证码存入session
        httpServletRequest.getSession().setAttribute("EmailCode",code.toString());

    }
}