package com.zte.km.dto.mq;

public enum MessageType {
    Order(1),OrderItem(2),OrderShipping(3);

    private Integer value;

    private MessageType(Integer value) {
        this.value=value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
