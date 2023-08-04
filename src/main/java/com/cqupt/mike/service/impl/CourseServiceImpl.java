package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.CategoryLevelEnum;
import com.cqupt.mike.common.MikeException;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.dao.CourseCategoryMapper;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.entity.CourseCategory;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.util.MikeUtils;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public String saveCourse(Course course) {
        //调用数据库添加课程信息
        if (courseMapper.insertSelective(course) > 0) {
            //成功则返回success
            return ServiceResultEnum.SUCCESS.getResult();
        }
        //添加失败
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCourse(Course course) {
        CourseCategory courseCategory = courseCategoryMapper.selectByPrimaryKey(course.getCourseCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (courseCategory == null || courseCategory.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        Course temp = courseMapper.selectByPrimaryKey(course.getCourseId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        Course temp2 = courseMapper.selectByCategoryIdAndName(course.getCourseName(), course.getCourseCategoryId());
        if (temp2 != null && !temp2.getCourseId().equals(course.getCourseId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        course.setCourseName(MikeUtils.cleanString(course.getCourseName()));
        course.setCourseIntro(MikeUtils.cleanString(course.getCourseIntro()));
        course.setTag(MikeUtils.cleanString(course.getTag()));
        course.setUpdateTime(new Date());
        if (courseMapper.updateByPrimaryKeySelective(course) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public PageResult getCoursePage(PageQueryUtil pageUtil) {
        List<Course> courseList = courseMapper.findCourseList(pageUtil);
        int total = courseMapper.getTotalCourse(pageUtil);
        PageResult pageResult = new PageResult(courseList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return courseMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }


    @Override
    public Course getCourseById(Long id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null) {
            MikeException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return course;
    }

}
