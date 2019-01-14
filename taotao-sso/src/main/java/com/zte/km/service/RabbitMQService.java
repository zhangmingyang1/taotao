package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zte.km.dao.UserMessageAddressMapper;
import com.zte.km.dao.UserMessageImgMapper;
import com.zte.km.dao.UserMessageMapper;
import com.zte.km.dto.mq.MessageData;
import com.zte.km.dto.mq.MessageType;
import com.zte.km.entities.UserMessage;
import com.zte.km.entities.UserMessageAddress;
import com.zte.km.entities.UserMessageImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQService {

    @Autowired
    private UserMessageMapper userMessageMapper;
    @Autowired
    private UserMessageImgMapper userMessageImgMapper;
    @Autowired
    private UserMessageAddressMapper userMessageAddressMapper;

    //1.处理消息数据
    public boolean processMessage(MessageData data) {
        if (data == null || data.getData() == null)
            return false;
        MessageType type = data.getType();
        switch (type){
            case UserMessage:
                JSONObject userMessageObject= (JSONObject) data.getData();
                UserMessage userMessage = userMessageObject.toJavaObject(UserMessage.class);
                userMessageMapper.insertOrUpdate(userMessage);
                break;
            case UserMessageImg:
                JSONObject userMessageImgObject= (JSONObject) data.getData();
                UserMessageImg userMessageImg = userMessageImgObject.toJavaObject(UserMessageImg.class);
                userMessageImgMapper.insertOrUpdate(userMessageImg);
                break;
            case UserMessageAddress:
                JSONArray jsonArray= (JSONArray) data.getData();
                List<UserMessageAddress> userMessageAddressList = JSONArray.parseArray(jsonArray.toJSONString(), UserMessageAddress.class);
                //1.删除所有用户收货地址
                userMessageAddressMapper.delete(userMessageAddressList.get(0).getUserId());
                //2.添加用户收货地址
                userMessageAddressList.forEach(x -> userMessageAddressMapper.insertOrUpdate(x));
                break;
            default:break;
        }
        return false;
    }
}
