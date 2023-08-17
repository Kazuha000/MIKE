package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.*;
import com.cqupt.mike.controller.vo.*;
import com.cqupt.mike.dao.CartItemMapper;
import com.cqupt.mike.dao.CourseMapper;
import com.cqupt.mike.dao.OrderItemMapper;
import com.cqupt.mike.dao.OrderMapper;
import com.cqupt.mike.entity.Course;
import com.cqupt.mike.entity.Order;
import com.cqupt.mike.entity.OrderItem;
import com.cqupt.mike.entity.StockNumDTO;
import com.cqupt.mike.service.OrderService;
import com.cqupt.mike.util.BeanUtil;
import com.cqupt.mike.util.NumberUtil;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper OrderMapper;
    @Resource
    private OrderItemMapper OrderItemMapper;
    @Resource
    private CartItemMapper CartItemMapper;
    @Resource
    private CourseMapper CourseMapper;
    @Override
    public PageResult getOrdersPage(PageQueryUtil pageUtil) {
        List<Order> Orders = OrderMapper.findOrderList(pageUtil);
        int total = OrderMapper.getTotalOrders(pageUtil);
        PageResult pageResult = new PageResult(Orders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(Order Order) {
        Order temp = OrderMapper.selectByPrimaryKey(Order.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(Order.getTotalPrice());
            temp.setUserEmail(Order.getUserEmail());
            temp.setUpdateTime(new Date());
            if (OrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order Order : orders) {
                if (Order.getIsDeleted() == 1) {
                    errorOrderNos += Order.getOrderNo() + " ";
                    continue;
                }
                if (Order.getOrderStatus() != 1) {
                    errorOrderNos += Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (OrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order Order : orders) {
                if (Order.getIsDeleted() == 1) {
                    errorOrderNos += Order.getOrderNo() + " ";
                    continue;
                }
                if (Order.getOrderStatus() != 1 && Order.getOrderStatus() != 2) {
                    errorOrderNos += Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (OrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = OrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order Order : orders) {
                // isDeleted=1 一定为已关闭订单
                if (Order.getIsDeleted() == 1) {
                    errorOrderNos += Order.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (Order.getOrderStatus() == 4 || Order.getOrderStatus() < 0) {
                    errorOrderNos += Order.getOrderNo() + " ";
                }
            }
            if (!StringUtils.hasText(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间&&恢复库存
                if (OrderMapper.closeOrder(Arrays.asList(ids), OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0 && recoverStockNum(Arrays.asList(ids))) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String saveOrder(MikeStudentVo user, List<CartItemVO> myCartItems) {
        List<Long> itemIdList = myCartItems.stream().map(CartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> courseIds = myCartItems.stream().map(CartItemVO::getCourseId).collect(Collectors.toList());
        List<Course> Course1 = CourseMapper.selectByPrimaryKeys(courseIds);
        //检查是否包含已下架商品
        List<Course> courseListNotSelling = Course1.stream()
                .filter(CourseTemp -> CourseTemp.getCourseSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(courseListNotSelling)) {
            //courseListNotSelling 对象非空则表示有下架商品
            MikeException.fail(courseListNotSelling.get(0).getCourseName() + "已下架，无法生成订单");
        }
        Map<Long, Course> CourseMap = Course1.stream().collect(Collectors.toMap(Course::getCourseId, Function.identity(), (entity1, entity2) -> entity1));
//        Map<Long, Course1> CourseMap = Course1.stream().collect(Collectors.toMap(Course1::ge, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (CartItemVO CartItemVO : myCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!CourseMap.containsKey(CartItemVO.getCourseId())) {
                MikeException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (CartItemVO.getCourseCount() > CourseMap.get(CartItemVO.getCourseId()).getStockNum()) {
                MikeException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(courseIds) && !CollectionUtils.isEmpty(Course1)) {
            if (CartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myCartItems, StockNumDTO.class);
                int updateStockNumResult = CourseMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    MikeException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                Order Order = new Order();
                Order.setOrderNo(orderNo);
                Order.setUserId(user.getStId());
                Order.setUserEmail(user.getEmail());
                //总价
                for (CartItemVO CartItemVO : myCartItems) {
                    priceTotal += CartItemVO.getCourseCount() * CartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    MikeException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                Order.setTotalPrice(priceTotal);
                //订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                Order.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (OrderMapper.insertSelective(Order) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<OrderItem> OrderItems = new ArrayList<>();
                    for (CartItemVO CartItemVO : myCartItems) {
                        OrderItem OrderItem = new OrderItem();
                        //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                        BeanUtil.copyProperties(CartItemVO, OrderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        OrderItem.setOrderId(Order.getOrderId());
                        OrderItems.add(OrderItem);
                    }
                    //保存至数据库
                    if (OrderItemMapper.insertBatch(OrderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    MikeException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MikeException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            MikeException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        MikeException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Integer userId) {
        Order newBeeMallOrder = OrderMapper.selectByOrderNo(orderNo);
        if (newBeeMallOrder == null) {
            MikeException.fail(ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult());
        }
        //验证是否是当前userId下的订单，否则报错
        if (!userId.equals(newBeeMallOrder.getUserId())) {
            MikeException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        List<OrderItem> orderItems = OrderItemMapper.selectByOrderId(newBeeMallOrder.getOrderId());
        //获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            MikeException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
        OrderDetailVO OrderDetailVO = new OrderDetailVO();
        BeanUtil.copyProperties(newBeeMallOrder, OrderDetailVO);
        OrderDetailVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(OrderDetailVO.getOrderStatus()).getName());
        OrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(OrderDetailVO.getPayType()).getName());
        OrderDetailVO.setOrderItemVOS(OrderItemVOS);
        return OrderDetailVO;
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return OrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = OrderMapper.getTotalOrders(pageUtil);
        List<Order> Orders = OrderMapper.findOrderList(pageUtil);
        List<OrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(Orders, OrderListVO.class);
            //设置订单状态中文显示值
            for (OrderListVO OrderListVO : orderListVOS) {
                OrderListVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(OrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = Orders.stream().map(Order::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<OrderItem> orderItems = OrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<OrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(OrderItem::getOrderId));
                for (OrderListVO OrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(OrderListVO.getOrderId())) {
                        List<OrderItem> orderItemListTemp = itemByOrderIdMap.get(OrderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItemListTemp, OrderItemVO.class);
                        OrderListVO.setOrderItemVOS(OrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String cancelOrder(String orderNo, Integer userId) {
        Order Order = OrderMapper.selectByOrderNo(orderNo);
        if (Order != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(Order.getUserId())) {
                MikeException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || Order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            //修改订单状态&&恢复库存
            if (OrderMapper.closeOrder(Collections.singletonList(Order.getOrderId()), OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0 && recoverStockNum(Collections.singletonList(Order.getOrderId()))) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Integer userId) {
        Order Order = OrderMapper.selectByOrderNo(orderNo);
        if (Order != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(Order.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            Order.setOrderStatus((byte) OrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            Order.setUpdateTime(new Date());
            if (OrderMapper.updateByPrimaryKeySelective(Order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        Order Order = OrderMapper.selectByOrderNo(orderNo);
        if (Order != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            Order.setOrderStatus((byte) OrderStatusEnum.ORDER_PAID.getOrderStatus());
            Order.setPayType((byte) payType);
            Order.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            Order.setPayTime(new Date());
            Order.setUpdateTime(new Date());
            if (OrderMapper.updateByPrimaryKeySelective(Order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<OrderItemVO> getOrderItems(Long id) {
        Order Order = OrderMapper.selectByPrimaryKey(id);
        if (Order != null) {
            List<OrderItem> orderItems = OrderItemMapper.selectByOrderId(Order.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
                return OrderItemVOS;
            }
        }
        return null;
    }

    /**
     * 恢复库存
     * @param orderIds
     * @return
     */
    public Boolean recoverStockNum(List<Long> orderIds) {
        //查询对应的订单项
        List<OrderItem> OrderItems = OrderItemMapper.selectByOrderIds(orderIds);
        //获取对应的商品id和商品数量并赋值到StockNumDTO对象中
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(OrderItems, StockNumDTO.class);
        //执行恢复库存的操作
        int updateStockNumResult = CourseMapper.recoverStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
            MikeException.fail(ServiceResultEnum.CLOSE_ORDER_ERROR.getResult());
            return false;
        } else {
            return true;
        }
    }

    public boolean checkoutPaySuccess(int userId,Long courseId){
        List<Order> orderList = OrderMapper.selectByUserId(userId);
        for (Order order : orderList) {
            if (order.getOrderStatus() == 4) {
                List<OrderItem> orderItemList = OrderItemMapper.selectByOrderId(order.getOrderId());
                for (OrderItem orderItem : orderItemList) {
                    if (Objects.equals(orderItem.getCourseId(), courseId)) {
                        return true;
                    }

                }
            }
        }
        return false;

    }


}
