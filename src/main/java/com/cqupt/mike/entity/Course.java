package com.cqupt.mike.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Course{

    private Long courseId;//课程表主键

    private String courseName;//课程名

    private String courseIntro;//课程简介

    private Long teacherId;//关联教师id

    private Long courseCategoryId;//关联分类id

    private String courseCoverImg;//课程主图

    private String courseCarousel;//课程轮播图

    private String courseDetailContent;//课程详情

    private Integer originalPrice;//课程价格

    private Integer sellingPrice;//实际售价

    private Integer stockNum;//课程库存数量

    private String tag;//标签

    private Byte courseSellStatus;//课程上架状态 0-下架 1-上架/-1-待审核

    private Integer createUser;//创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间

    private Integer updateUser;//更新者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;//更新时间

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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseCategoryId() {
        return courseCategoryId;
    }

    public void setCourseCategoryId(Long courseCategoryId) {
        this.courseCategoryId = courseCategoryId;
    }

    public String getCourseCoverImg() {
        return courseCoverImg;
    }

    public void setCourseCoverImg(String courseCoverImg) {
        this.courseCoverImg = courseCoverImg;
    }

    public String getCourseCarousel() {
        return courseCarousel;
    }

    public void setCourseCarousel(String courseCarousel) {
        this.courseCarousel = courseCarousel;
    }

    public String getCourseDetailContent() {
        return courseDetailContent;
    }

    public void setCourseDetailContent(String courseDetailContent) {
        this.courseDetailContent = courseDetailContent;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Byte getCourseSellStatus() {
        return courseSellStatus;
    }

    public void setCourseSellStatus(Byte courseSellStatus) {
        this.courseSellStatus = courseSellStatus;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseIntro='" + courseIntro + '\'' +
                ", teacherId=" + teacherId +
                ", courseCategoryId=" + courseCategoryId +
                ", courseCoverImg='" + courseCoverImg + '\'' +
                ", courseCarousel='" + courseCarousel + '\'' +
                ", courseDetailContent='" + courseDetailContent + '\'' +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", stockNum=" + stockNum +
                ", tag='" + tag + '\'' +
                ", courseSellStatus=" + courseSellStatus +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
