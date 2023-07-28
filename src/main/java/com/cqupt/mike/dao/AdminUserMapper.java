package com.cqupt.mike.dao;

import com.cqupt.mike.entity.AdminUser;

import java.util.List;

public interface AdminUserMapper {


    /**
     * 管理员登陆
     * @param adName 通过管理员姓名查找
     * @return 返回所有符合条件的管理员列表
     */
    List<AdminUser> login( String adName);

    AdminUser selectByPrimaryKey(Integer adminUserId);


    int insert(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
    


}