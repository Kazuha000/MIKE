package com.cqupt.mike.service;

import com.cqupt.mike.controller.vo.CartItemVO;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.controller.vo.OrderDetailVO;
import com.cqupt.mike.controller.vo.OrderItemVO;
import com.cqupt.mike.entity.Order;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;

import java.util.List;

public interface OrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param Order
     * @return
     */
    String updateOrderInfo(Order Order);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     *
     * @param user
     * @param myCartItems
     * @return
     */
    String saveOrder(MikeStudentVo user, List<CartItemVO> myCartItems);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Integer userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Integer userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Integer userId);

    String paySuccess(String orderNo, int payType);

    List<OrderItemVO> getOrderItems(Long id);

    /**
     *
     * @param user_id
     * @return
     */
     boolean checkoutPaySuccess(int user_id,Long course_id);

}
