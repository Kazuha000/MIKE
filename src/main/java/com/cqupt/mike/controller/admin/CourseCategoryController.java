package com.cqupt.mike.controller.admin;

import com.cqupt.mike.common.CategoryLevelEnum;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class CourseCategoryController {


        @Resource
        private CategoryService categoryService;

        @GetMapping("/categories")
        public String categoriesPage(HttpServletRequest request, @RequestParam("categoryLevel") Byte categoryLevel, @RequestParam("parentId") Long parentId, @RequestParam("backParentId") Long backParentId) {
            if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
                return "error/error_5xx";
            }
            request.setAttribute("path", "category");
            request.setAttribute("parentId", parentId);
            request.setAttribute("backParentId", backParentId);
            request.setAttribute("categoryLevel", categoryLevel);
            return "admin/category";
        }

        /**
         * 列表
         */
        @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
        @ResponseBody
        public Result list(@RequestParam Map<String, Object> params) {
            if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("categoryLevel")) || StringUtils.isEmpty(params.get("parentId"))) {
                return ResultGenerator.genFailResult("参数异常！");
            }
            PageQueryUtil pageUtil = new PageQueryUtil(params);
            return ResultGenerator.genSuccessResult(categoryService.getCategorisPage(pageUtil));
        }

        /**
         * 添加
         */
        @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
        @ResponseBody
        public Result save(@RequestBody CourseCategory courseCategory) {
            if (Objects.isNull(courseCategory.getCategoryLevel())
                    || StringUtils.isEmpty(courseCategory.getCategoryName())
                    || Objects.isNull(courseCategory.getParentId())
                    || Objects.isNull(courseCategory.getCategoryRank())) {
                return ResultGenerator.genFailResult("参数异常！");
            }
            String result = categoryService.saveCategory(courseCategory);
            if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult(result);
            }
        }


            /**
             * 修改
             */
            @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
            @ResponseBody
            public Result update(@RequestBody CourseCategory courseCategory) {
                if (Objects.isNull(courseCategory.getCategoryId())
                        || Objects.isNull(courseCategory.getCategoryLevel())
                        || StringUtils.isEmpty(courseCategory.getCategoryName())
                        || Objects.isNull(courseCategory.getParentId())
                        || Objects.isNull(courseCategory.getCategoryRank())) {
                    return ResultGenerator.genFailResult("参数异常！");
                }
                String result = categoryService.updateCourseCategory(courseCategory);
                if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
                    return ResultGenerator.genSuccessResult();
                } else {
                    return ResultGenerator.genFailResult(result);
                }
            }

            /**
             * 详情
             */
            @GetMapping("/categories/info/{id}")
            @ResponseBody
            public Result info(@PathVariable("id") Long id) {
                CourseCategory courseCategory = categoryService.getCourseCategoryById(id);
                if (courseCategory == null) {
                    return ResultGenerator.genFailResult("未查询到数据");
                }
                return ResultGenerator.genSuccessResult(courseCategory);
            }

            /**
             * 分类删除
             */
            @RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
            @ResponseBody
            public Result delete(@RequestBody Integer[] ids) {
                if (ids.length < 1) {
                    return ResultGenerator.genFailResult("参数异常！");
                }
                if (categoryService.deleteBatch(ids)) {
                    return ResultGenerator.genSuccessResult();
                } else {
                    return ResultGenerator.genFailResult("删除失败");
                }
            }

}
