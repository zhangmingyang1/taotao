package com.zte.km.controller;

import com.zte.km.dto.CartItem;
import com.zte.km.dto.CartOrder;
import com.zte.km.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1.展示购物车订单
     * @param model
     * @return 购物车订单
     */
    @RequestMapping(value = "/cartOrder",method = RequestMethod.GET)
    public String cartOrder(Model model){
        List<CartItem> cartItemList=orderService.getCartItemList();
        model.addAttribute("cartList",cartItemList);
        return "cartOrder";
    }

    //2.创建订单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createOrder(CartOrder cartOrder,Model model){
        if (cartOrder == null || cartOrder.getOrder()==null || cartOrder.getOrderItems()==null || cartOrder.getOrderShipping()==null) {
            model.addAttribute("message","订单创建发生异常，请您稍后再试！");
            return "error/exception";
        }
        try {
            String OrderId=orderService.createOrder(cartOrder);
            model.addAttribute("orderId",OrderId);
            model.addAttribute("payment",cartOrder.getOrder().getPayment());
            //预计送达时间
            model.addAttribute("data",new Date().toString());
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("message","订单创建发生异常，请您稍后再试！");
            return "error/exception";
        }
        return "success";
    }

}
