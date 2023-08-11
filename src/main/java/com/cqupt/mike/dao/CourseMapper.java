package com.cqupt.mike.dao;

import com.cqupt.mike.entity.Carousel;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//课程管理
public interface CourseMapper {

    /**
     * 添加课程
     * @param record
     * @return
     */
    int insertSelective(Course record);

    /**
     * 更新课程
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Course record);

    /**
     * 查询分页数据
     * @param pageUtil
     * @return
     */
    List<Course> findCourseList(PageQueryUtil pageUtil);

    /**
     * 通过教师id查询分页数据
     * @param start
     * @param limit
     * @param teacherId
     * @return
     */
    List<Course> findCourseListByTeacherId(@Param("start") int start,@Param("limit") int limit,@Param("teacherId") int teacherId);

    /**
     * 查询课程总数
     * @param pageUtil
     * @return
     */
    int getTotalCourse(PageQueryUtil pageUtil);

    /**
     * 根据搜索字段查询分页数据
     * @param pageUtil
     * @return
     */
    List<Course> findCourseListBySearch(PageQueryUtil pageUtil);


    /**
     * 根据搜索字段查询总数
     * @param pageUtil
     * @return
     */
    int getTotalCourseBySearch(PageQueryUtil pageUtil);

    /**
     * 批量修改课程状态
     * @param courseIds
     * @param sellStatus
     * @return
     */
    int batchUpdateSellStatus(@Param("courseIds")Long[] courseIds, @Param("sellStatus") int sellStatus);

    /**
     * 通过id查询课程
     * @param courseId
     * @return
     */
    Course selectByPrimaryKey(Long courseId);

    /**
     * 通过id列表查询课程列表
     * @param courseId
     * @return
     */
    List<Course> selectByPrimaryKeys(List<Long> courseId);

    /**
     * 通过课程分类id与课程名查询课程
     * @param courseName
     * @param courseCategoryId
     * @return
     */
    Course selectByCategoryIdAndName(@Param("courseName") String courseName, @Param("courseCategoryId") Long courseCategoryId);



}
