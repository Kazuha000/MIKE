package com.cqupt.mike.service;

import com.cqupt.mike.entity.AdminUser;

import java.util.List;

public interface AdminUserService {


    /**
     *管理员登录
     * @param adName 管理员用户名
     * @param password 密码
     * @return 登录成功返回管理员id，密码错误返回0，用户不存在返回-1
     */
    int login(String adName,String password);

    /**
     * 通过id查询管理员用户信息
     * @param adId 管理员id
     * @return 返回管理员用户信息
     */
    public AdminUser getUserDetailById(Integer adId);

    /**
     * 修改用户密码
     * @param adId 管理员id
     * @param originalPassword 旧密码
     * @param newPassword 新密码
     * @return 若修改成功，则返回true
     */
    public Boolean updatePassword(Integer adId, String originalPassword, String newPassword);

    /**
     *修改用户名与账号
     * @param adId 管理员id
     * @param adName 用户名
     * @param accountNo 账号
     * @return 修改成功，返回true
     */
    public Boolean updateName(Integer adId, String adName, Integer accountNo);
}
