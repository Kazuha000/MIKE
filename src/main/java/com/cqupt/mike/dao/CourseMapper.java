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
     * 查询课程总数
     * @param pageUtil
     * @return
     */
    int getTotalCourse(PageQueryUtil pageUtil);

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
     * 通过课程分类id与课程名查询课程
     * @param courseName
     * @param courseCategoryId
     * @return
     */
    Course selectByCategoryIdAndName(@Param("courseName") String courseName, @Param("courseCategoryId") Long courseCategoryId);



}
