package com.cqupt.mike.dao;

import com.cqupt.mike.entity.Carousel;
import com.cqupt.mike.entity.Course;
//课程管理
public interface CourseMapper {

    /**
     * 保存一条新记录
     * @param record
     * @return
     */
    int insertSelective(Course record);
}
