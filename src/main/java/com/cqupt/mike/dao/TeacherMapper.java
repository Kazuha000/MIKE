package com.cqupt.mike.dao;

import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.entity.Student;
import com.cqupt.mike.entity.Teacher;
import com.cqupt.mike.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    /**
     * 返回数据列表
     *
     * @return
     */
    List<Teacher> findAllTeachers();

    /**
     * 添加
     *
     * @param teacher
     * @return
     */
    int insertTeacher(Teacher teacher);

    /**
     * 修改
     *
     * @param teacher
     * @return
     */
    int updTeacher(Teacher teacher);


    /**
     *
     * @param pageUtil
     * @return
     */
    List<Teacher> findTeacherList(PageQueryUtil pageUtil);

    /**
     *
     * @param pageUtil
     * @return
     */
    int getTotalTeachers(PageQueryUtil pageUtil);

    /**
     * 修改状态
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    int lockTeacherBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);




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