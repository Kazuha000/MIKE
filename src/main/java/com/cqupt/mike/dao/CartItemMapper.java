package com.cqupt.mike.dao;

import com.cqupt.mike.entity.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(CartItem record);

    int insertSelective(CartItem record);

    CartItem selectByPrimaryKey(Long cartItemId);

    CartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    List<CartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

    int selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(CartItem record);

    int updateByPrimaryKey(CartItem record);

    int deleteBatch(List<Long> ids);
}
