package com.zte.km.dao;

import com.zte.km.entities.UserMessageAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageAddressMapper {

    //1.新增/更新用户收货地址信息
    void insertOrUpdate(@Param("userMessageAddress") UserMessageAddress userMessageAddress);

    //2.删除所有用户收货地址
    void delete(@Param("userId") Long userId);

    //3.根据ID查询所有用户收货地址
    List<UserMessageAddress> getUserMessageAddressList(@Param("userId")Long userId);

}