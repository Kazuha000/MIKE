package com.cqupt.mike.service;



import com.cqupt.mike.controller.vo.IndexConfigCourseVO;
import com.cqupt.mike.entity.IndexConfig;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

import java.util.List;

public interface IndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品配置
     * @param indexConfig
     * @return
     */
    String saveIndexConfig(IndexConfig indexConfig);

    /**
     * 修改
     * @param indexConfig
     * @return
     */
    String updateIndexConfig(IndexConfig indexConfig);

    /**
     * 通过id获取
     * @param id
     * @return
     */
    IndexConfig getIndexConfigById(Long id);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexConfigCourseVO> getConfigCourseForIndex(int configType, int number);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteBatch(Long[] ids);
}
