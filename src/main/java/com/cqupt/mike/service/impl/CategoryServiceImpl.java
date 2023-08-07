package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.CategoryLevelEnum;
import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.IndexCategoryVO;
import com.cqupt.mike.controller.vo.SearchPageCategoryVO;
import com.cqupt.mike.controller.vo.SecondLevelCategoryVO;
import com.cqupt.mike.controller.vo.ThirdLevelCategoryVO;
import com.cqupt.mike.dao.CourseCategoryMapper;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.util.BeanUtil;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CategoryServiceImpl implements CategoryService{

        @Resource
        private CourseCategoryMapper courseCategoryMapper;

        @Override
        public PageResult getCategorisPage(PageQueryUtil pageUtil) {
            List<CourseCategory> CourseCategories = courseCategoryMapper.findCourseCategoryList(pageUtil);
            int total = courseCategoryMapper.getTotalCourseCategories(pageUtil);
            PageResult pageResult = new PageResult(CourseCategories, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }

        @Override
        public String saveCategory(CourseCategory CourseCategory) {
            CourseCategory temp = courseCategoryMapper.selectByLevelAndName(CourseCategory.getCategoryLevel(), CourseCategory.getCategoryName());
            if (temp != null) {
                return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
            }
            if (courseCategoryMapper.insertSelective(CourseCategory) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }

        @Override
        public String updateCourseCategory(CourseCategory CourseCategory) {
            CourseCategory temp = courseCategoryMapper.selectByPrimaryKey(CourseCategory.getCategoryId());
            if (temp == null) {
                return ServiceResultEnum.DATA_NOT_EXIST.getResult();
            }
            CourseCategory temp2 = courseCategoryMapper.selectByLevelAndName(CourseCategory.getCategoryLevel(), CourseCategory.getCategoryName());
            if (temp2 != null && !temp2.getCategoryId().equals(CourseCategory.getCategoryId())) {
                //同名且不同id 不能继续修改
                return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
            }
            CourseCategory.setUpdateTime(new Date());
            if (courseCategoryMapper.updateByPrimaryKeySelective(CourseCategory) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }

        @Override
        public CourseCategory getCourseCategoryById(Long id) {
            return courseCategoryMapper.selectByPrimaryKey(id);
        }

        @Override
        public Boolean deleteBatch(Integer[] ids) {
            if (ids.length < 1) {
                return false;
            }
            //删除分类数据
            return courseCategoryMapper.deleteBatch(ids) > 0;
        }

        @Override
        public List<CourseCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
            return courseCategoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);//0代表查询所有
        }
        /**
         * 返回分类数据(首页调用)
         *
         * @return
         */
        public List<IndexCategoryVO> getCategoriesForIndex(){
            List<IndexCategoryVO> indexCategoryVOS = new ArrayList<>();
            //获取一级分类的固定数量的数据
            List<CourseCategory> firstLevelCategories = courseCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel(), Constants.INDEX_CATEGORY_NUMBER);
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(CourseCategory::getCategoryId).collect(Collectors.toList());
                //获取二级分类的数据
                List<CourseCategory> secondLevelCategories = courseCategoryMapper.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, CategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(CourseCategory::getCategoryId).collect(Collectors.toList());
                    //获取三级分类的数据
                    List<CourseCategory> thirdLevelCategories = courseCategoryMapper.selectByLevelAndParentIdsAndNumber(secondLevelCategoryIds, CategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                    if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<CourseCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(CourseCategory::getParentId));
                        List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                        //处理二级分类
                        for (CourseCategory secondLevelCategory : secondLevelCategories) {
                            SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                            BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                            //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                            if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                                //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                                List<CourseCategory> tempCourseCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                                secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempCourseCategories, ThirdLevelCategoryVO.class)));
                                secondLevelCategoryVOS.add(secondLevelCategoryVO);
                            }
                        }
                        //处理一级分类
                        if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                            //根据 parentId 将 thirdLevelCategories 分组
                            Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                            for (CourseCategory firstCategory : firstLevelCategories) {
                                IndexCategoryVO indexCategoryVO = new IndexCategoryVO();
                                BeanUtil.copyProperties(firstCategory, indexCategoryVO);
                                //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                                if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                    //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                    List<SecondLevelCategoryVO> tempCourseCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                    indexCategoryVO.setSecondLevelCategoryVOS(tempCourseCategories);
                                    indexCategoryVOS.add(indexCategoryVO);
                                }
                            }
                        }
                    }
                }
                return indexCategoryVOS;
            } else {
                return null;
            }
        }

        /**
         * 返回分类数据(搜索页调用)
         *
         * @param categoryId
         * @return
         */
        public SearchPageCategoryVO getCategoriesForSearch(Long categoryId){
            SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
            CourseCategory thirdLevelCourseCategory = courseCategoryMapper.selectByPrimaryKey(categoryId);
            if (thirdLevelCourseCategory != null && thirdLevelCourseCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
                //获取当前三级分类的二级分类
                CourseCategory secondLevelCourseCategory = courseCategoryMapper.selectByPrimaryKey(thirdLevelCourseCategory.getParentId());
                if (secondLevelCourseCategory != null && secondLevelCourseCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
                    //获取当前二级分类下的三级分类List
                    List<CourseCategory> thirdLevelCategories = courseCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCourseCategory.getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel(), Constants.SEARCH_CATEGORY_NUMBER);
                    searchPageCategoryVO.setCurrentCategoryName(thirdLevelCourseCategory.getCategoryName());
                    searchPageCategoryVO.setSecondLevelCategoryName(secondLevelCourseCategory.getCategoryName());
                    searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                    return searchPageCategoryVO;
                }
            }
            return null;
        }
    }


