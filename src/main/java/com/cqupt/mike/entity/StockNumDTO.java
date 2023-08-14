package com.cqupt.mike.entity;

/**
 * 库存修改所需实体
 */
public class StockNumDTO {
    private Long courseId;

    private Integer courseCount;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}