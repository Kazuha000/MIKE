package com.cqupt.mike.controller.admin;

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
@RequestMapping("/admin")
public class CourseStatusController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private CourseService courseService;




    @GetMapping("/course")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "course");
        return "admin/course";
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
