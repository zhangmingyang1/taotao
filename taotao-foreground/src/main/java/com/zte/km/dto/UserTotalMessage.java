package com.zte.km.dto;

import java.util.List;

public class UserTotalMessage extends User{
    private UserMessage userMessage;
    private UserMessageImg userMessageImg;
    private List<UserMessageAddress> userMessageAddressList;

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public UserMessageImg getUserMessageImg() {
        return userMessageImg;
    }

    public void setUserMessageImg(UserMessageImg userMessageImg) {
        this.userMessageImg = userMessageImg;
    }

    public List<UserMessageAddress> getUserMessageAddressList() {
        return userMessageAddressList;
    }

    public void setUserMessageAddressList(List<UserMessageAddress> userMessageAddressList) {
        this.userMessageAddressList = userMessageAddressList;
    }
}
