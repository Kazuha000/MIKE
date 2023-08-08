package com.cqupt.mike.service;

import com.cqupt.mike.entity.Course;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

public interface CourseService {

    /**
     * 添加课程
     * @param course
     * @return
     */
    String saveCourse(Course course);

    /**
     * 修改商品信息
     *
     * @param course
     * @return
     */
    String updateCourse(Course course);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCoursePage(PageQueryUtil pageUtil);

    /**
     * 教师个人课程获取分页
     * @param pageUtil
     * @param teacherId
     * @return
     */

    PageResult getCoursePage(PageQueryUtil pageUtil,int teacherId);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 通过id获取课程详情
     *
     * @param id
     * @return
     */
    Course getCourseById(Long id);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchCourse(PageQueryUtil pageUtil);


}
