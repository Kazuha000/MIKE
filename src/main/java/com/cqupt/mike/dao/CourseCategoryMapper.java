package com.cqupt.mike.dao;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCategoryMapper {
        /**
         * 删除一条记录
         *
         * @param categoryId
         * @return
         */
        int deleteByPrimaryKey(Long categoryId);

        /**
         * 保存一条新记录
         *
         * @param record
         * @return
         */
        int insert(CourseCategory record);

        /**
         * 保存一条新记录
         *
         * @param record
         * @return
         */
        int insertSelective(CourseCategory record);

        /**
         * 根据主键查询记录
         *
         * @param categoryId
         * @return
         */
        CourseCategory selectByPrimaryKey(Long categoryId);

        /**
         * 根据分类等级和分类名称查询一条分类记录
         *
         * @param categoryLevel
         * @param categoryName
         * @return
         */
        CourseCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

        /**
         * 修改记录
         *
         * @param record
         * @return
         */
        int updateByPrimaryKeySelective(CourseCategory record);

        /**
         * 修改记录
         *
         * @param record
         * @return
         */
        int updateByPrimaryKey(CourseCategory record);

        /**
         * 查询分页数据
         *
         * @param pageUtil
         * @return
         */
        List<CourseCategory> findCourseCategoryList(PageQueryUtil pageUtil);

        /**
         * 查询总数
         *
         * @param pageUtil
         * @return
         */
        int getTotalCourseCategories(PageQueryUtil pageUtil);

        /**
         * 批量删除
         *
         * @param ids
         * @return
         */
        int deleteBatch(Integer[] ids);

        /**
         * 根据父类分类id、分类等级和数量查询分类列表
         *
         * @param parentIds
         * @param categoryLevel
         * @param number
         * @return
         */
        List<CourseCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
    }


