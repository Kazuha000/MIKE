package com.cqupt.mike.service;

import com.cqupt.mike.entity.CartItem;

import java.util.List;

public interface CartService {

    /**
     * 保存商品至购物车中
     *
     * @param cartItem
     * @return
     */
    String saveNewBeeMallCartItem(CartItem cartItem);

    /**
     * 修改购物车中的属性
     *
     * @param cartItem
     * @return
     */
    String updateNewBeeMallCartItem(CartItem cartItem);

    /**
     * 获取购物项详情
     *
     * @param newBeeMallShoppingCartItemId
     * @return
     */
    CartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param newBeeMallUserId
     * @return
     */
//    List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);
}