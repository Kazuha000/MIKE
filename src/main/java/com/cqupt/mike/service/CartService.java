package com.cqupt.mike.service;

import com.cqupt.mike.controller.vo.CartItemVO;
import com.cqupt.mike.entity.CartItem;

import java.util.List;

public interface CartService {

    /**
     * 保存课程至购物车中
     *
     * @param cartItem
     * @return
     */
    String saveCartItem(CartItem cartItem);

    /**
     * 修改购物车中的属性
     *
     * @param cartItem
     * @return
     */
    String updateCartItem(CartItem cartItem);

    /**
     * 获取购物项详情
     *
     * @param CartItemId
     * @return
     */
    CartItem getCartItemById(Long CartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param CartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long CartItemId, Integer userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param userId
     * @return
     */
    List<CartItemVO> getMyShoppingCartItems(Integer userId);
}