package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.dao.CourseCategoryMapper;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

        @Resource
        private CourseCategoryMapper CourseCategoryMapper;

        @Override
        public PageResult getCategorisPage(PageQueryUtil pageUtil) {
            List<CourseCategory> CourseCategories = CourseCategoryMapper.findCourseCategoryList(pageUtil);
            int total = CourseCategoryMapper.getTotalCourseCategories(pageUtil);
            PageResult pageResult = new PageResult(CourseCategories, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }

        @Override
        public String saveCategory(CourseCategory CourseCategory) {
            CourseCategory temp = CourseCategoryMapper.selectByLevelAndName(CourseCategory.getCategoryLevel(), CourseCategory.getCategoryName());
            if (temp != null) {
                return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
            }
            if (CourseCategoryMapper.insertSelective(CourseCategory) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }

        @Override
        public String updateCourseCategory(CourseCategory CourseCategory) {
            CourseCategory temp = CourseCategoryMapper.selectByPrimaryKey(CourseCategory.getCategoryId());
            if (temp == null) {
                return ServiceResultEnum.DATA_NOT_EXIST.getResult();
            }
            CourseCategory temp2 = CourseCategoryMapper.selectByLevelAndName(CourseCategory.getCategoryLevel(), CourseCategory.getCategoryName());
            if (temp2 != null && !temp2.getCategoryId().equals(CourseCategory.getCategoryId())) {
                //同名且不同id 不能继续修改
                return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
            }
            CourseCategory.setUpdateTime(new Date());
            if (CourseCategoryMapper.updateByPrimaryKeySelective(CourseCategory) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }

        @Override
        public CourseCategory getCourseCategoryById(Long id) {
            return CourseCategoryMapper.selectByPrimaryKey(id);
        }

        @Override
        public Boolean deleteBatch(Integer[] ids) {
            if (ids.length < 1) {
                return false;
            }
            //删除分类数据
            return CourseCategoryMapper.deleteBatch(ids) > 0;
        }

        @Override
        public List<CourseCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
            return CourseCategoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);//0代表查询所有
        }
    }


