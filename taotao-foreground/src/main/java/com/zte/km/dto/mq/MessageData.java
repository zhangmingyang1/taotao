package com.zte.km.dto.mq;

public class MessageData<T> {
    private MessageType type;
    private T data;

    public MessageData() {
    }

    public MessageData(MessageType type, T data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
