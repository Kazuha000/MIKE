package com.cqupt.mike.dao;

import com.cqupt.mike.entity.Student;

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
}
