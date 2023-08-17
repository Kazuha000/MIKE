package com.cqupt.mike.service;

import com.cqupt.mike.controller.vo.IndexCategoryVO;
import com.cqupt.mike.controller.vo.SearchPageCategoryVO;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

import java.util.List;

/**
 * 课程分类
 */

public interface CategoryService {
        /**
         * 查询后台管理系统分类分页数据
         *
         * @param pageUtil
         * @return
         */
        PageResult getCategorisPage(PageQueryUtil pageUtil);

        /**
         * 新增一条分类记录
         *
         * @param CourseCategory
         * @return
         */
        String saveCategory(CourseCategory CourseCategory);

        /**
         * 修改一条分类记录
         *
         * @param CourseCategory
         * @return
         */
        String updateCourseCategory(CourseCategory CourseCategory);

        /**
         * 根据主键查询分类记录
         *
         * @param id
         * @return
         */
        CourseCategory getCourseCategoryById(Long id);

        /**
         * 批量删除分类数据
         *
         * @param ids
         * @return
         */
        Boolean deleteBatch(Integer[] ids);

        /**
         * 根据parentId和level获取分类列表
         *
         * @param parentIds
         * @param categoryLevel
         * @return
         */
        List<CourseCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);

        /**
         * 返回分类数据(首页调用)
         *
         * @return
         */
        List<IndexCategoryVO> getCategoriesForIndex();

        /**
         * 返回分类数据(搜索页调用)
         *
         * @param categoryId
         * @return
         */
        SearchPageCategoryVO getCategoriesForSearch(Long categoryId);

    }


