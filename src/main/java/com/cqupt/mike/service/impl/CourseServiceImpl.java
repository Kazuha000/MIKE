package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.MikeException;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Override
    public String saveCourse(Course course) {
        //调用数据库添加课程信息
        if (courseMapper.insertSelective(course) > 0) {
            //成功则返回success
            return ServiceResultEnum.SUCCESS.getResult();
        }
        //添加失败
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public PageResult getCoursePage(PageQueryUtil pageUtil) {
        List<Course> courseList = courseMapper.findCourseList(pageUtil);
        int total = courseMapper.getTotalCourse(pageUtil);
        PageResult pageResult = new PageResult(courseList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return courseMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }


    @Override
    public Course getCourseById(Long id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null) {
            MikeException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return course;
    }

}
