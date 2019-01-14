package com.zte.km.dao;

import com.zte.km.entities.UserMessage;
import org.apache.ibatis.annotations.Param;

public interface UserMessageMapper {

    //1.新增/更新用户基本信息
    void insertOrUpdate(@Param("userMessage") UserMessage userMessage);

    //2.根据ID查询用户基本信息
    UserMessage getUserMessage(@Param("userId")Long userId);
}