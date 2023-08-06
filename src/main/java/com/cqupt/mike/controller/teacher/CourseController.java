package com.cqupt.mike.controller.teacher;

import com.cqupt.mike.common.CategoryLevelEnum;
import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.util.PageQueryUtil;
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
import java.util.Map;
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

    @GetMapping("/course/edit/{courseId}")
    public String edit(HttpServletRequest request, @PathVariable("courseId") Long courseId) {
        request.setAttribute("path", "edit");
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return "error/error_400";
        }
        if (course.getCourseCategoryId() > 0) {
            if (course.getCourseCategoryId() != null || course.getCourseCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                CourseCategory currentCourseCategory = categoryService.getCourseCategoryById(course.getCourseCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentCourseCategory != null && currentCourseCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<CourseCategory> firstLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<CourseCategory> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentCourseCategory.getParentId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    CourseCategory secondCategory = categoryService.getCourseCategoryById(currentCourseCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<CourseCategory> secondLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        CourseCategory firestCategory = categoryService.getCourseCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentCourseCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (course.getCourseCategoryId() == 0) {
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
                }
            }
        }
        request.setAttribute("course", course);
        request.setAttribute("path", "course-edit");
        return "teacher/course_edit";
    }

    /**
     * 修改课程信息
     */
    @RequestMapping(value = "/course/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Course course) {
        if (Objects.isNull(course.getCourseId())
                || StringUtils.isEmpty(course.getCourseName())
                || StringUtils.isEmpty(course.getCourseIntro())
                || StringUtils.isEmpty(course.getTag())
                || Objects.isNull(course.getOriginalPrice())
                || Objects.isNull(course.getSellingPrice())
                || Objects.isNull(course.getCourseCategoryId())
                || Objects.isNull(course.getStockNum())
                || Objects.isNull(course.getCourseSellStatus())
                || StringUtils.isEmpty(course.getCourseCoverImg())
                || StringUtils.isEmpty(course.getCourseDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = courseService.updateCourse(course);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/course")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "course");
        return "teacher/course";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(courseService.getCoursePage(pageUtil));
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/course/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (courseService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}
