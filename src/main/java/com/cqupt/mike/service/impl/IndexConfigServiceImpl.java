package com.cqupt.mike.service.impl;


import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.IndexConfigCourseVO;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.dao.IndexConfigMapper;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.entity.IndexConfig;
import com.cqupt.mike.service.IndexConfigService;
import com.cqupt.mike.util.BeanUtil;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexConfigServiceImpl implements IndexConfigService {

    @Resource
    private IndexConfigMapper indexConfigMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        if (courseMapper.selectByPrimaryKey(indexConfig.getCourseId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndCourseId(indexConfig.getConfigType(), indexConfig.getCourseId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        if (courseMapper.selectByPrimaryKey(indexConfig.getCourseId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndCourseId(indexConfig.getConfigType(), indexConfig.getCourseId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    public List<IndexConfigCourseVO> getConfigCourseForIndex(int configType, int number) {
        List<IndexConfigCourseVO> indexConfigCourseVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getCourseId).collect(Collectors.toList());
            List<Course> Course = courseMapper.selectByPrimaryKeys(goodsIds);
            indexConfigCourseVOS = BeanUtil.copyList(Course, IndexConfigCourseVO.class);
            for (IndexConfigCourseVO indexConfigCourseVO : indexConfigCourseVOS) {
                String courseName = indexConfigCourseVO.getCourseName();
                String courseIntro = indexConfigCourseVO.getCourseIntro();
                // 字符串过长导致文字超出的问题
                if (courseName.length() > 30) {
                    courseName = courseName.substring(0, 30) + "...";
                    indexConfigCourseVO.setCourseName(courseName);
                }
                if (courseIntro.length() > 22) {
                    courseIntro = courseIntro.substring(0, 22) + "...";
                    indexConfigCourseVO.setCourseIntro(courseIntro);
                }
            }
        }
        return indexConfigCourseVOS;
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
