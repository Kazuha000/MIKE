package com.cqupt.mike.controller.vo;

import java.io.Serializable;
/**
 * 搜索列表页商品VO
 */
public class SearchCourseVO implements Serializable {

    private Long courseId;

    private String courseName;

    private String courseIntro;

    private String courseCoverImg;

    private Integer sellingPrice;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseIntro() {
        return courseIntro;
    }
    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getCourseCoverImg() {
        return courseCoverImg;
    }

    public void setCourseCoverImg(String courseCoverImg) {
        this.courseCoverImg = courseCoverImg;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}