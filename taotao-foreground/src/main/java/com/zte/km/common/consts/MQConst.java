package com.zte.km.common.consts;

/**
 * 设定rabbitMQ常量
 */
public class MQConst {
    //交换机
    public static final String EXCHANGE="MyExchange";
    //订单
    public static final String ORDER_QUEUE="ORDER_QUEUE";
    public static final String ORDER_ROUTING_KEY="ORDER_KEY";
    //用户
    public static final String USER_QUEUE="USER_QUEUE";
    public static final String USER_ROUTING_KEY="USER_KEY";

    public MQConst() {

    }

}
