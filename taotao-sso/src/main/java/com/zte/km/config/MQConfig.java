package com.zte.km.config;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zte.km.common.consts.MQConst;
import com.zte.km.dto.mq.MessageData;
import com.zte.km.entities.User;
import com.zte.km.service.RabbitMQService;
import com.zte.km.service.UserService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/6/30.
 */
@Configuration
public class MQConfig {

    @Value("${spring.rabbitmq.host}")
    public String host;
    @Value("${spring.rabbitmq.port}")
    public String port;
    @Value("${spring.rabbitmq.username}")
    public String username;
    @Value("${spring.rabbitmq.password}")
    public String password;

    @Autowired
    private RabbitMQService rabbitMQService;

    //配置交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MQConst.EXCHANGE);
    }
    //配置连接工厂

    //配置队列
    @Bean
    public Queue queue(){
        return new Queue(MQConst.USER_QUEUE,true);
    }

    //将队列绑定到交换机
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MQConst.USER_ROUTING_KEY);
    }
    //消费者，实时消息监听

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory=new CachingConnectionFactory(host,Integer.valueOf(port));
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer messageListener=new SimpleMessageListenerContainer(connectionFactory());
        messageListener.setQueues(queue());
        messageListener.setExposeListenerChannel(true);
        messageListener.setConcurrentConsumers(1);
        messageListener.setMaxConcurrentConsumers(10);
        messageListener.setAcknowledgeMode(AcknowledgeMode.MANUAL);    //手工确认
        messageListener.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String msg = new String(message.getBody(), "UTF-8");
                System.out.println("---接收消息message:"+msg);
                MessageData data = JSON.parseObject(msg).toJavaObject(MessageData.class);
                //1.处理消息数据
                rabbitMQService.processMessage(data);
                //2.手工确认消息接收成功
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        });
        return messageListener;
    }

}
