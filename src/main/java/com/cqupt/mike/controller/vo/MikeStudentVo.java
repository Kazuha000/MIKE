package com.cqupt.mike.controller.vo;

import java.io.Serializable;
//用于向主页传入学生相关参数
public class MikeStudentVo implements Serializable { //Serializable序列化接口,将对象状态转换为可保持或传输的格式

    private Integer stId;  //主键，学生id

    private Integer accountNo;  //账号

    private String stName;  //学生姓名

    private Integer sex;  //性别:0-未知/1-男/2-女

    private String phone;  //电话

    private String email;  //邮箱

    private int shopCartItemCount; //购物车物品数量

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getShopCartItemCount() {
        return shopCartItemCount;
    }

    public void setShopCartItemCount(int shopCartItemCount) {
        this.shopCartItemCount = shopCartItemCount;
    }
}
