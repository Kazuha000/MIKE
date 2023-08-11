package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.CartItemVO;
import com.cqupt.mike.dao.CartItemMapper;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.entity.CartItem;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.service.CartService;
import com.cqupt.mike.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartItemMapper cartItemMapper;

    @Resource
    private CourseMapper CourseMapper;
    @Override
    public String saveCartItem(CartItem CartItem) {
        CartItem temp = cartItemMapper.selectByUserIdAndCourseId(CartItem.getUserId(), CartItem.getCourseId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setCourseCount(CartItem.getCourseCount());
            return updateCartItem(temp);
        }
        Course Course = CourseMapper.selectByPrimaryKey(CartItem.getCourseId());
        //商品为空
        if (Course == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = cartItemMapper.selectCountByUserId(CartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (CartItem.getCourseCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (cartItemMapper.insertSelective(CartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    @Override
    public String updateCartItem(CartItem CartItem) {
        CartItem CartItemUpdate = cartItemMapper.selectByPrimaryKey(CartItem.getCartItemId());
        if (CartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (CartItem.getCourseCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!CartItemUpdate.getUserId().equals(CartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (CartItem.getCourseCount().equals(CartItemUpdate.getCourseCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        CartItemUpdate.setCourseCount(CartItem.getCourseCount());
        CartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (cartItemMapper.updateByPrimaryKeySelective(CartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public CartItem getCartItemById(Long CartItemId) {
        return cartItemMapper.selectByPrimaryKey(CartItemId);
    }

    @Override
    public Boolean deleteById(Long CartItemId, Integer userId) {
        CartItem CartItem = cartItemMapper.selectByPrimaryKey(CartItemId);
        if (CartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(CartItem.getUserId())) {
            return false;
        }
        return cartItemMapper.deleteByPrimaryKey(CartItemId) > 0;
    }

    @Override
    public List<CartItemVO> getMyShoppingCartItems(Integer userId) {
        List<CartItemVO> CartItemVOS = new ArrayList<>();
        List<CartItem> CartItems = cartItemMapper.selectByUserId(userId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(CartItems)) {
            //查询商品信息并做数据转换
            List<Long> CourseIds = CartItems.stream().map(CartItem::getCourseId).collect(Collectors.toList());
            List<Course> Courses = CourseMapper.selectByPrimaryKeys(CourseIds);
            Map<Long, Course> CourseMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(Courses)) {
                CourseMap = Courses.stream().collect(Collectors.toMap(Course::getCourseId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (CartItem CartItem : CartItems) {
                CartItemVO CartItemVO = new CartItemVO();
                BeanUtil.copyProperties(CartItem, CartItemVO);
                if (CourseMap.containsKey(CartItem.getCourseId())) {
                    Course CourseTemp = CourseMap.get(CartItem.getCourseId());
                    CartItemVO.setCourseCoverImg(CourseTemp.getCourseCoverImg());
                    String courseName = CourseTemp.getCourseName();
                    // 字符串过长导致文字超出的问题
                    if (courseName.length() > 28) {
                        courseName = courseName.substring(0, 28) + "...";
                    }
                    CartItemVO.setCourseName(courseName);
                    CartItemVO.setSellingPrice(CourseTemp.getSellingPrice());
                    CartItemVOS.add(CartItemVO);
                }
            }
        }
        return CartItemVOS;
    }
}