package com.cqupt.mike.dao;

import com.cqupt.mike.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    /**
     * 返回数据列表
     *
     * @return
     */
    List<Student> findAllStudents();

    /**
     * 添加
     *
     * @param student
     * @return
     */
    int insertStudent(Student student);

    /**
     * 修改
     *
     * @param student
     * @return
     */
    int updStudent(Student student);

    /**
     * 删除
     *
     * @param stId
     * @return
     */
    int delStudent(Integer stId);


    /**
     * 根据stName查询记录
     * @param stName
     * @return
     */
    Student selectByLoginName(String stName);

    /**
     * 根据stName和密码字段查询记录
     * @param stName
     * @return
     */
    Student selectByLoginNameAndPasswd(@Param("stName") String stName, @Param("password") String password);
}
