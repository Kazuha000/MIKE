package com.cqupt.mike.controller.mike;

import com.cqupt.mike.common.Constants;
import com.cqupt.mike.common.MikeException;
import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.CartItemVO;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.entity.CartItem;
import com.cqupt.mike.service.CartService;
import com.cqupt.mike.util.Result;
import com.cqupt.mike.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Resource
    private CartService CartService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<CartItemVO> myShoppingCartItems = CartService.getMyShoppingCartItems(user.getStId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(CartItemVO::getCourseCount).sum();
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
            //总价
            for (CartItemVO CartItemVO : myShoppingCartItems) {
                priceTotal += CartItemVO.getCourseCount() * CartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mike/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveCartItem(@RequestBody CartItem CartItem,
                               HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        CartItem.setUserId(user.getStId());
        String saveResult = CartService.saveCartItem(CartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    /**
     * 修改购物项
     */
    @PutMapping("/shop-cart")
    @ResponseBody
    public Result CartItem(@RequestBody CartItem CartItem,
                           HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        CartItem.setUserId(user.getStId());
        String updateResult = CartService.updateCartItem(CartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    /**
     * 删除购物项
     */
    @DeleteMapping("/shop-cart/{CartItemId}")
    @ResponseBody
    public Result CartItem(@PathVariable("CartItemId") Long CartItemId,
                           HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        Boolean deleteResult = CartService.deleteById(CartItemId ,user.getStId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        List<CartItemVO> myCartItems = CartService.getMyShoppingCartItems(user.getStId());
        if (CollectionUtils.isEmpty(myCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (CartItemVO CartItemVO : myCartItems) {
                priceTotal += CartItemVO.getCourseCount() * CartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MikeException.fail("购课项价格异常");
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myCartItems", myCartItems);
        return "mike/order-settle";
    }
}
