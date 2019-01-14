package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.zte.km.common.consts.MQConst;
import com.zte.km.dto.mq.MessageData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQService implements RabbitTemplate.ConfirmCallback {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    //发布订单消息
    public void SendOrderMessage(MessageData message){
        //执行保存
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //在发布消息之前，必须指定确认消息回调的对象
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(MQConst.EXCHANGE, MQConst.ORDER_ROUTING_KEY, JSON.toJSONString(message),correlationId);
    }

    //发布用户消息
    public void SendUserMessage(MessageData message){
        //执行保存
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //在发布消息之前，必须指定确认消息回调的对象
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(MQConst.EXCHANGE, MQConst.USER_ROUTING_KEY, JSON.toJSONString(message),correlationId);
    }

    //消息发送确认
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack){
            System.out.println("---消息发送成功:"+correlationData);
        } else {
            System.out.println("---消息发送失败:"+cause);
        }
    }

}