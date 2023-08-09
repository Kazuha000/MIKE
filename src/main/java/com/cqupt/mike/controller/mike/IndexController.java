package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.IndexConfigTypeEnum;
import com.cqupt.mike.common.MikeException;
import com.cqupt.mike.controller.vo.IndexCarouselVO;
import com.cqupt.mike.controller.vo.IndexCategoryVO;
import com.cqupt.mike.controller.vo.IndexConfigCourseVO;
import com.cqupt.mike.service.CarouselService;
import com.cqupt.mike.service.CategoryService;
import com.cqupt.mike.service.IndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
public class IndexController {
    @Resource
    private CarouselService carouselService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping({"/index", "/", "/index.html"})//MIKE主页跳转
    public String indexPage(HttpServletRequest request) {

        List<IndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MikeException.fail("分类数据不完善");
        }
        //返回固定数量的轮播图对象(首页调用)
        List<IndexCarouselVO> carousels = carouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<IndexConfigCourseVO> hotCourse = indexConfigService.getConfigCourseForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_COURSES_HOT_NUMBER);
        List<IndexConfigCourseVO> newCourse = indexConfigService.getConfigCourseForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_COURSES_NEW_NUMBER);
        List<IndexConfigCourseVO> recommendCourse = indexConfigService.getConfigCourseForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_COURSES_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotCourse", hotCourse);//热销课程
        request.setAttribute("newCourse", newCourse);//新品
        request.setAttribute("recommendCourse", recommendCourse);//推荐课程
        return "mike/index";
    }
}
