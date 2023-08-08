package com.cqupt.mike.controller.vo;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 */
public class CartItemVO implements Serializable {

    private Long cartItemId;

    private Long coursesId;

    private Integer coursesCount;

    private String coursesName;

    private String coursesCoverImg;

    private Integer sellingPrice;

    public Long getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Long coursesId) {
        this.coursesId = coursesId;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public void setCoursesName(String coursesName) {
        this.coursesName = coursesName;
    }

    public String getCoursesCoverImg() {
        return coursesCoverImg;
    }

    public void setCoursesCoverImg(String coursesCoverImg) {
        this.coursesCoverImg = coursesCoverImg;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getCoursesCount() {
        return coursesCount;
    }

    public void setCoursesCount(Integer coursesCount) {
        this.coursesCount = coursesCount;
    }
}
