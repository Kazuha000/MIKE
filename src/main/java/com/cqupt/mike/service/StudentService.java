package com.cqupt.mike.service;

import javax.servlet.http.HttpSession;

public interface StudentService {

    /**
     * 用户注册
     *
     * @param stName
     * @param password
     * @return
     */
    String register(String stName, String password);

    /**
     * 登录
     *
     * @param stName
     * @param password
     * @param httpSession
     * @return
     */
    String login(String stName, String password, HttpSession httpSession);
}
