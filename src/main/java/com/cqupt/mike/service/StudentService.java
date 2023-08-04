package com.cqupt.mike.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface StudentService {

    /**
     * 用户注册
     *
     * @param stName 用户名
     * @param password 密码
     * @return 登陆结果
     */
    String register(String stName, String password);

    /**
     * 登录
     *
     * @param stName 用户名
     * @param password 密码
     * @param httpSession session
     * @return 登陆结果
     */
    String login(String stName, String password, HttpSession httpSession);

    /**
     *忘记密码
     *
     * @param stName 用户名
     * @param email 邮箱
     * @return 登陆结果
     */
    String forgetpassword(String stName, String email, HttpSession httpSession,HttpServletRequest httpServletRequest);

    /**
     * 重置密码
     * @param stId 用户ID
     * @param newpassword 新密码
     * @return
     */
    String resetpassword(int stId,String newpassword);
}
