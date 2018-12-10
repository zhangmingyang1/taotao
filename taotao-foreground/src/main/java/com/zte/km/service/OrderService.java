package com.zte.km.service;

import com.zte.km.common.utils.CookieUtils;
import com.zte.km.dto.*;
import com.zte.km.dto.mq.MessageData;
import com.zte.km.dto.mq.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private CartService cartItemList;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitMQService rabbitMQService;

    //1.购物车订单
    public List<CartItem> getCartItemList() {
        return cartItemList.getCartList();
    }

    //2.创建订单
    public String createOrder(CartOrder cartOrder) {
        User user =(User)request.getAttribute("user");
        Order order = cartOrder.getOrder();
        List<OrderItem> orderItems = cartOrder.getOrderItems();
        OrderShipping orderShipping = cartOrder.getOrderShipping();
        //redis生成订单ID
        Long ORDER_INDEX = redisTemplate.opsForValue().increment("ORDER_INDEX", 1);
        order.setOrderId(ORDER_INDEX.toString());
        order.setStatus(1);    //支付状态：1、未付款
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());     //买家昵称
        order.setBuyerRate(0);    //买家是否已经评价
        rabbitMQService.convertAndSend(new MessageData(MessageType.Order,order));
        //-----------------------订单明细--------------------------//
        orderItems.forEach(x -> {
            //redis生成订单明细ID
            Long ORDER_ITEM_INDEX = redisTemplate.opsForValue().increment("ORDER_ITEM_INDEX", 1);
            x.setId(ORDER_ITEM_INDEX.toString());
            x.setOrderId(ORDER_INDEX.toString());
            rabbitMQService.convertAndSend(new MessageData(MessageType.OrderItem,x));
        });
        //--------------------------订单物流-----------------------//
        orderShipping.setOrderId(ORDER_INDEX.toString());
        rabbitMQService.convertAndSend(new MessageData(MessageType.OrderShipping,orderShipping));
        //下订单，删除购物车商品
        CookieUtils.deleteCookie(request,response,"CART");
        return ORDER_INDEX.toString();
    }

}
