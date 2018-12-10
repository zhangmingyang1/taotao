package com.zte.km.dao;

import com.zte.km.entities.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //1.检查用户登陆信息
    User checkByName(@Param("content") String content);

    User checkByPhone(@Param("content") String content);

    User checkByEmail(@Param("content") String content);

    //2.用户注册
    boolean register(@Param("user") User user);

}