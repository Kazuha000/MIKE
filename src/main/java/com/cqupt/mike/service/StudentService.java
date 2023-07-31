package com.cqupt.mike.service;

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
}
