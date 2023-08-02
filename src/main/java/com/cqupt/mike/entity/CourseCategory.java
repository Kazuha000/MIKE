package com.cqupt.mike.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CourseCategory {

        private Long categoryId;//分类id

        private Byte categoryLevel;//分类级别(1-一级分类 2-二级分类 3-三级分类)

        private Long parentId;//父分类id

        private String categoryName;//分类名称

        private Integer categoryRank;//排序值(字段越大越靠前)

        private Byte isDeleted;//删除标识字段(0-未删除 1-已删除)

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date createTime;//创建时间

        private Integer createUser;//创建者id

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date updateTime;//修改时间

        private Integer updateUser;//修改者id

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public Byte getCategoryLevel() {
            return categoryLevel;
        }

        public void setCategoryLevel(Byte categoryLevel) {
            this.categoryLevel = categoryLevel;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName == null ? null : categoryName.trim();
        }

        public Integer getCategoryRank() {
            return categoryRank;
        }

        public void setCategoryRank(Integer categoryRank) {
            this.categoryRank = categoryRank;
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
    }

