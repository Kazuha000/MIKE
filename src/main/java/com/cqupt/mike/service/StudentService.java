package com.cqupt.mike.service;

import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface StudentService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getstudentPage(PageQueryUtil pageUtil);
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
     * 忘记密码
     *
     * @param stName 用户名
     * @param email 邮箱
     * @param httpSession httpsession
     * @param httpServletRequest httpservletrequest
     * @return
     */
    String forgetpassword(String stName, String email, HttpSession httpSession, HttpServletRequest httpServletRequest);

    /**
     ** 用户禁用与解除禁用(0-未锁定 1-已锁定)
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
