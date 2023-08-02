package com.cqupt.mike.controller.teacher;

import com.cqupt.mike.common.CategoryLevelEnum;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/teacher")
public class CourseController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private CourseService courseService;


    @GetMapping("/course/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<CourseCategory> firstLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<CourseCategory> secondLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<CourseCategory> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "course-edit");
                return "teacher/course_edit";
            }
        }
        return "error/error_5xx";
    }

    /**
     * 添加课程
     */
    @RequestMapping(value = "/course/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Course course) {
        //判断参数是否为空
        if (StringUtils.isEmpty(course.getCourseName())
                || StringUtils.isEmpty(course.getCourseIntro())
                || StringUtils.isEmpty(course.getTag())
                || Objects.isNull(course.getOriginalPrice())
                || Objects.isNull(course.getCourseCategoryId())
                || Objects.isNull(course.getSellingPrice())
                || Objects.isNull(course.getStockNum())
                || Objects.isNull(course.getCourseSellStatus())
                || StringUtils.isEmpty(course.getCourseCoverImg())
                || StringUtils.isEmpty(course.getCourseDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        //向数据库新增一个课程信息
        String result = courseService.saveCourse(course);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
}
