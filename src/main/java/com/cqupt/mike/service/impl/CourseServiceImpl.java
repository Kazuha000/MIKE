package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
