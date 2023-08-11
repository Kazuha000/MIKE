package com.cqupt.mike.controller.mike;


import com.cqupt.mike.common.Constants;
import com.cqupt.mike.controller.vo.CourseDetailVO;
import com.cqupt.mike.controller.vo.SearchPageCategoryVO;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.util.BeanUtil;
import com.cqupt.mike.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MikeCourseController {

    @Resource
    private CourseService CourseService;
    @Resource
    private CategoryService CategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.COURSES_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("courseCategoryId") && !StringUtils.isEmpty(params.get("courseCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("courseCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = CategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("courseCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", CourseService.searchCourse(pageUtil));
        return "mike/search";
    }
    @GetMapping("/course/detail/{courseId}")
    public String detailPage(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        if (courseId < 1) {
            return "error/error_5xx";
        }
        Course course = CourseService.getCourseById(courseId);
        if (course == null) {
            return "error/error_404";
        }
        CourseDetailVO courseDetailVO = new CourseDetailVO();
        BeanUtil.copyProperties(course, courseDetailVO);
        request.setAttribute("courseDetail", courseDetailVO);
        return "mike/detail";
    }


}