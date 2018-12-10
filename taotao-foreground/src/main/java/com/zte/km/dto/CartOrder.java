package com.zte.km.dto;

import java.util.List;

/**
 * 购物车下订单信息
 * @author 梨花雨凉、
 */
public class CartOrder {

    //1.订单信息
    private Order order;

    //2.商品信息
   private List<OrderItem> orderItems;

   //3.订单物流信息
    private OrderShipping orderShipping;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
