package com.cqupt.mike.dao;

import com.cqupt.mike.entity.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartItemMapper {
    /**
     * 删除一条记录
     *
     * @param cartItemId
     * @return
     */
    int deleteByPrimaryKey(Long cartItemId);

    int insert(CartItem record);

    /**
     * 保存一条新记录
     *
     * @param record
     * @return
     */
    int insertSelective(CartItem record);

    /**
     * 根据主键查询记录
     *
     * @param cartItemId
     * @return
     */
    CartItem selectByPrimaryKey(Long cartItemId);

    /**
     * 根据userId和courseId查询记录
     *
     * @param userId
     * @param courseId
     * @return
     */
    CartItem selectByUserIdAndCourseId(@Param("userId") Integer userId, @Param("courseId") Long courseId);

    /**
     * 根据userId和number字段获取固定数量的购物项列表数据
     * @param userId
     * @param number
     * @return
     */
    List<CartItem> selectByUserId(@Param("userId") Integer userId, @Param("number") int number);

    /**
     * 根据userId查询当前用户已添加了多少条记录
     *
     * @param userId
     * @return
     */
    int selectCountByUserId(Integer userId);

    /**
     * 修改记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CartItem record);

    int updateByPrimaryKey(CartItem record);

    int deleteBatch(List<Long> ids);
}