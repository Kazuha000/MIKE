package com.cqupt.mike.dao;

import com.cqupt.mike.entity.Student;
import com.cqupt.mike.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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

    /**
     * 根据id查询记录
     * @param stId
     * @return
     */
    Student selectById(@Param("stId") int stId);

    Student selectByPrimaryKey(Integer userId);

    /**
     * 列表
     * @param pageUtil
     * @return
     */
    List<Student> findstudentList(PageQueryUtil pageUtil);

    /**
     * 查询学生总数
     * @param pageUtil
     * @return
     */
    int getTotalStudent(PageQueryUtil pageUtil);

    /**
     * 锁定学生
     * @param ids
     * @param status
     * @return
     */
    int lockUserBatch(@Param("ids") Integer[] ids, @Param("status") int status);

    int updateByPrimaryKeySelective(Student record);


}
