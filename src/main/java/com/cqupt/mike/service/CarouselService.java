package com.cqupt.mike.service;

import com.cqupt.mike.controller.vo.IndexCarouselVO;
import com.cqupt.mike.entity.Carousel;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

import java.util.List;


public interface CarouselService {
    /**
     * 查询后台管理系统轮播图分页数据
     *
     * @param pageUtil 分页信息
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    /**
     * 新增一条轮播图记录
     *
     * @param carousel  轮播图
     * @return
     */
    String saveCarousel(Carousel carousel);

    /**
     * 修改一条轮播图记录
     *
     * @param carousel 轮播图
     * @return
     */
    String updateCarousel(Carousel carousel);

    /**
     * 根据主键查询轮播图记录
     *
     * @param id
     * @return
     */
    Carousel getCarouselById(Integer id);

    /**
     * 批量删除轮播图记录
     *
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     * @param number
     * @return
     */
    public List<IndexCarouselVO> getCarouselsForIndex(int number);
}
