//package com.cqupt.mike.service.impl;
//
//import com.cqupt.mike.common.Constants;
//import com.cqupt.mike.common.ServiceResultEnum;
//import com.cqupt.mike.dao.CartItemMapper;
//import com.cqupt.mike.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Service
//public class CartServiceImpl implements CartService {
//
//    @Autowired
//    private CartItemMapper cartItemMapper;
//
//    @Autowired
//    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;
//
//    @Override
//    public String saveNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
//        NewBeeMallShoppingCartItem temp = cartItemMapper.selectByUserIdAndGoodsId(newBeeMallShoppingCartItem.getUserId(), newBeeMallShoppingCartItem.getGoodsId());
//        if (temp != null) {
//            //已存在则修改该记录
//            temp.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
//            return updateNewBeeMallCartItem(temp);
//        }
//        NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getGoodsId());
//        //商品为空
//        if (newBeeMallGoods == null) {
//            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
//        }
//        int totalItem = cartItemMapper.selectCountByUserId(newBeeMallShoppingCartItem.getUserId()) + 1;
//        //超出单个商品的最大数量
//        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
//            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
//        }
//        //超出最大数量
//        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
//            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
//        }
//        //保存记录
//        if (cartItemMapper.insertSelective(newBeeMallShoppingCartItem) > 0) {
//            return ServiceResultEnum.SUCCESS.getResult();
//        }
//        return ServiceResultEnum.DB_ERROR.getResult();
//    }
//
//    @Override
//    public String updateNewBeeMallCartItem(NewBeeMallShoppingCartItem newBeeMallShoppingCartItem) {
//        NewBeeMallShoppingCartItem newBeeMallShoppingCartItemUpdate = cartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItem.getCartItemId());
//        if (newBeeMallShoppingCartItemUpdate == null) {
//            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
//        }
//        //超出单个商品的最大数量
//        if (newBeeMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
//            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
//        }
//        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
//        if (!newBeeMallShoppingCartItemUpdate.getUserId().equals(newBeeMallShoppingCartItem.getUserId())) {
//            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
//        }
//        //数值相同，则不执行数据操作
//        if (newBeeMallShoppingCartItem.getGoodsCount().equals(newBeeMallShoppingCartItemUpdate.getGoodsCount())) {
//            return ServiceResultEnum.SUCCESS.getResult();
//        }
//        newBeeMallShoppingCartItemUpdate.setGoodsCount(newBeeMallShoppingCartItem.getGoodsCount());
//        newBeeMallShoppingCartItemUpdate.setUpdateTime(new Date());
//        //修改记录
//        if (cartItemMapper.updateByPrimaryKeySelective(newBeeMallShoppingCartItemUpdate) > 0) {
//            return ServiceResultEnum.SUCCESS.getResult();
//        }
//        return ServiceResultEnum.DB_ERROR.getResult();
//    }
//
//    @Override
//    public NewBeeMallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
//        return cartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
//    }
//
//    @Override
//    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
//        NewBeeMallShoppingCartItem newBeeMallShoppingCartItem = cartItemMapper.selectByPrimaryKey(shoppingCartItemId);
//        if (newBeeMallShoppingCartItem == null) {
//            return false;
//        }
//        //userId不同不能删除
//        if (!userId.equals(newBeeMallShoppingCartItem.getUserId())) {
//            return false;
//        }
//        return cartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
//    }
//
//    @Override
//    public List<NewBeeMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
//        List<NewBeeMallShoppingCartItemVO> newBeeMallShoppingCartItemVOS = new ArrayList<>();
//        List<NewBeeMallShoppingCartItem> newBeeMallShoppingCartItems = cartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
//        if (!CollectionUtils.isEmpty(newBeeMallShoppingCartItems)) {
//            //查询商品信息并做数据转换
//            List<Long> newBeeMallGoodsIds = newBeeMallShoppingCartItems.stream().map(NewBeeMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
//            List<NewBeeMallGoods> newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
//            Map<Long, NewBeeMallGoods> newBeeMallGoodsMap = new HashMap<>();
//            if (!CollectionUtils.isEmpty(newBeeMallGoods)) {
//                newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(NewBeeMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
//            }
//            for (NewBeeMallShoppingCartItem newBeeMallShoppingCartItem : newBeeMallShoppingCartItems) {
//                NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO = new NewBeeMallShoppingCartItemVO();
//                BeanUtil.copyProperties(newBeeMallShoppingCartItem, newBeeMallShoppingCartItemVO);
//                if (newBeeMallGoodsMap.containsKey(newBeeMallShoppingCartItem.getGoodsId())) {
//                    NewBeeMallGoods newBeeMallGoodsTemp = newBeeMallGoodsMap.get(newBeeMallShoppingCartItem.getGoodsId());
//                    newBeeMallShoppingCartItemVO.setGoodsCoverImg(newBeeMallGoodsTemp.getGoodsCoverImg());
//                    String goodsName = newBeeMallGoodsTemp.getGoodsName();
//                    // 字符串过长导致文字超出的问题
//                    if (goodsName.length() > 28) {
//                        goodsName = goodsName.substring(0, 28) + "...";
//                    }
//                    newBeeMallShoppingCartItemVO.setGoodsName(goodsName);
//                    newBeeMallShoppingCartItemVO.setSellingPrice(newBeeMallGoodsTemp.getSellingPrice());
//                    newBeeMallShoppingCartItemVOS.add(newBeeMallShoppingCartItemVO);
//                }
//            }
//        }
//        return newBeeMallShoppingCartItemVOS;
//    }
//}