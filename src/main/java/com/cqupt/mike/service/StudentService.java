package com.cqupt.mike.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.entity.Student;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

public interface StudentService {
    /**
     * 列表
     * @param pageUtil
     * @return
     */
    PageResult getstudentPage (PageQueryUtil pageUtil);

    /**
     * 用户锁定控制
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
    /**
     * 用户注册
     *
     * @param stName 用户名
     * @param password 密码
     * @return 登陆结果
     */
    String register(String stName, String password,String email);

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
