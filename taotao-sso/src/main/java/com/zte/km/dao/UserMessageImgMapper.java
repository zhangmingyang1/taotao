package com.zte.km.dao;

import com.zte.km.entities.UserMessageImg;
import org.apache.ibatis.annotations.Param;

public interface UserMessageImgMapper {

    //1.新增/更新用户头像信息
    void insertOrUpdate(@Param("userMessageImg") UserMessageImg userMessageImg);

    //1.根据ID查询用户头像信息
    UserMessageImg getUserMessageImg(@Param("userId") Long userId);
}