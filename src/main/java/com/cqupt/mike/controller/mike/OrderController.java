package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.MikeException;
import com.cqupt.mike.common.OrderStatusEnum;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.CartItemVO;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.controller.vo.OrderDetailVO;
import com.cqupt.mike.entity.Order;
import com.cqupt.mike.service.CartService;
import com.cqupt.mike.service.OrderService;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Resource
    private CartService CartService;
    @Resource
    private OrderService OrderService;

    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        OrderDetailVO orderDetailVO = OrderService.getOrderDetailByOrderNo(orderNo, user.getStId());
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mike/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        params.put("userId", user.getStId());
        if (ObjectUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", OrderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mike/my-orders";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        List<CartItemVO> myCartItems = CartService.getMyShoppingCartItems(user.getStId());
        if (!StringUtils.hasText(user.getEmail().trim())) {
            //无收货地址
            MikeException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myCartItems)) {
            //购物车中无数据则跳转至错误页
            MikeException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = OrderService.saveOrder(user, myCartItems);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        String cancelOrderResult = OrderService.cancelOrder(orderNo, user.getStId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        String finishOrderResult = OrderService.finishOrder(orderNo, user.getStId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        Order Order = OrderService.getOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getStId().equals(Order.getUserId())) {
            MikeException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MikeException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", Order.getTotalPrice());
        return "mike/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        Order Order = OrderService.getOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getStId().equals(Order.getUserId())) {
            MikeException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MikeException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", Order.getTotalPrice());
        if (payType == 1) {
            return "mike/alipay";
        } else {
            return "mike/wxpay";
        }
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = OrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }
}
