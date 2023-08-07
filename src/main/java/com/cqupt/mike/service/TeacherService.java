package com.cqupt.mike.service;

import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.entity.Teacher;

public interface TeacherService {


    /**
     *管理员登陆
     * @param name 管理员用户名
     * @param password 密码
     * @return 登陆成功返回管理员id，密码错误返回0，用户不存在返回-1
     */
    int login(String name,String password);

    /**
     * 通过id查询管理员用户信息
     * @param id 管理员id
     * @return 返回管理员用户信息
     */
    public Teacher getUserDetailById(Integer id);

    /**
     * 修改用户密码
     * @param id 管理员id
     * @param originalPassword 旧密码
     * @param newPassword 新密码
     * @return 若修改成功，则返回true
     */
    public Boolean updatePassword(Integer id, String originalPassword, String newPassword);

    /**
     *修改用户名与账号
     * @param id 管理员id
     * @param name 用户名
     * @param accountNo 账号
     * @return 修改成功，返回true
     */
    public Boolean updateName(Integer id, String name, Integer accountNo);
}
