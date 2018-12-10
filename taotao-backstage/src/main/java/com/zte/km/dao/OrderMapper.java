package com.zte.km.dao;

import com.zte.km.entities.Order;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    //1.插入订单表
    void insert(@Param("order") Order order);

}