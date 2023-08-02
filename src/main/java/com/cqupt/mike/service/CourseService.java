package com.cqupt.mike.service;

import com.cqupt.mike.entity.Course;

public interface CourseService {

    /**
     * 添加课程
     * @param course
     * @return
     */
    String saveCourse(Course course);
}
