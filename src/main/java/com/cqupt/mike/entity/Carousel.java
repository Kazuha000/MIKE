package com.cqupt.mike.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Carousel {

    private Integer carouselId;//轮播图ID


    private String carouselUrl;//轮播图地址


    private String redirectUrl;//点击后的跳转地址

    private Integer carouselRank;//排序值(字段越大越靠前)

    private Byte isDeleted;//删除标识字段(0-未删除 1-已删除)

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

    private Integer createUser;//创建者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;//更新时间

    private Integer updateUser;//更新者

    public Integer getCarouselId() {
        return carouselId;
    }

    public void setCarouselId(Integer carouselId) {
        this.carouselId = carouselId;
    }

    public String getCarouselUrl() {
        return carouselUrl;
    }

    public void setCarouselUrl(String carouselUrl) {
        this.carouselUrl = carouselUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getCarouselRank() {
        return carouselRank;
    }

    public void setCarouselRank(Integer carouselRank) {
        this.carouselRank = carouselRank;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carouselId=" + carouselId +
                ", carouselUrl='" + carouselUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", carouselRank=" + carouselRank +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                '}';
    }
}
