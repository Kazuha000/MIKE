package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.controller.vo.IndexCarouselVO;
import com.cqupt.mike.service.CarouselService;
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

//    @Resource
//    private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

//    @Resource
//    private NewBeeMallCategoryService newBeeMallCategoryService;
    @GetMapping({"/index", "/", "/index.html"})//MIKE主页跳转
    public String indexPage(HttpServletRequest request) {

//        List<NewBeeMallIndexCategoryVO> categories = newBeeMallCategoryService.getCategoriesForIndex();
//        if (CollectionUtils.isEmpty(categories)) {
//            NewBeeMallException.fail("分类数据不完善");
//        }
        //返回固定数量的轮播图对象(首页调用)
        List<IndexCarouselVO> carousels = carouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
//        List<NewBeeMallIndexConfigGoodsVO> hotGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
//        List<NewBeeMallIndexConfigGoodsVO> newGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
//        List<NewBeeMallIndexConfigGoodsVO> recommendGoodses = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
//        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
//        request.setAttribute("hotGoodses", hotGoodses);//热销商品
//        request.setAttribute("newGoodses", newGoodses);//新品
//        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品
        return "mike/index";
    }
}
