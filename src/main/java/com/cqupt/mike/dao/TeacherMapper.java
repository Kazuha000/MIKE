package com.cqupt.mike.dao;

import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.entity.Teacher;

import java.util.List;

public interface TeacherMapper {


    /**
     * 教师登陆
     * @param name 通过姓名查找
     * @return 返回所有符合条件的教师列表
     */
    List<Teacher> login( String name);

    Teacher selectByPrimaryKey(Integer id);


    int insert(Teacher record);

    int updateByPrimaryKey(Teacher record);
    


}