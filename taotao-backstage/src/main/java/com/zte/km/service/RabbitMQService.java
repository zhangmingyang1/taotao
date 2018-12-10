package com.zte.km.service;

import com.alibaba.fastjson.JSONObject;
import com.zte.km.dao.OrderItemMapper;
import com.zte.km.dao.OrderMapper;
import com.zte.km.dao.OrderShippingMapper;
import com.zte.km.dto.mq.MessageData;
import com.zte.km.dto.mq.MessageType;
import com.zte.km.entities.Order;
import com.zte.km.entities.OrderItem;
import com.zte.km.entities.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    //1.处理消息数据
    public void processMessage(MessageData data) {
        if (data == null || data.getData() == null)
            return;
        MessageType type = data.getType();
        JSONObject jsonObject= (JSONObject) data.getData();
        switch (type){
            case Order:
                Order order = jsonObject.toJavaObject(Order.class);
                orderMapper.insert(order);
                break;
            case OrderItem:
                OrderItem orderItem = jsonObject.toJavaObject(OrderItem.class);
                orderItemMapper.insert(orderItem);
                break;
            case OrderShipping:
                OrderShipping orderShipping= jsonObject.toJavaObject(OrderShipping.class);
                orderShippingMapper.insert(orderShipping);
                break;
            default:break;
        }
    }
}
