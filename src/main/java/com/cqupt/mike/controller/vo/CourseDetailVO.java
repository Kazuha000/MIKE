package com.cqupt.mike.controller.vo;

import java.io.Serializable;

/**
 * 商品详情页VO
 */
public class CourseDetailVO implements Serializable {

    private Long courseId;

    private String courseName;

    private String courseIntro;

    private String courseCoverImg;

    private String[] courseCarouselList;

    private Integer sellingPrice;

    private Integer originalPrice;

    private String courseDetailContent;

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

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCourseDetailContent() {
        return courseDetailContent;
    }

    public void setCourseDetailContent(String courseDetailContent) {
        this.courseDetailContent = courseDetailContent;
    }

    public String[] getCourseCarouselList() {
        return courseCarouselList;
    }

    public void setCourseCarouselList(String[] courseCarouselList) {
        this.courseCarouselList = courseCarouselList;
    }
}