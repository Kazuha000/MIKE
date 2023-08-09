package com.cqupt.mike.dao;


import com.cqupt.mike.entity.IndexConfig;
import com.cqupt.mike.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author 张天宇
 * 主页信息配置mapper
 */
public interface IndexConfigMapper {
    /**
     * 通过id删除配置信息
     * @param configId
     * @return
     */
    int deleteByPrimaryKey(Long configId);

    /**
     * 插入配置信息
     * @param record
     * @return
     */
    int insertSelective(IndexConfig record);

    /**
     * 根据id查询配置信息
     * @param configId
     * @return
     */
    IndexConfig selectByPrimaryKey(Long configId);

    /**
     * 根据类型与课程id查询配置信息
     * @param configType
     * @param courseId
     * @return
     */
    IndexConfig selectByTypeAndCourseId(@Param("configType") int configType, @Param("courseId") Long courseId);

    /**
     * 更新配置信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(IndexConfig record);

    /**
     * 列表
     * @param pageUtil
     * @return
     */
    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    /**
     * 获取总数
     * @param pageUtil
     * @return
     */
    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteBatch(Long[] ids);

    /**
     * 通过类型和序号获取列表
     * @param configType
     * @param number
     * @return
     */
    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}