package com.zte.km.dao;

import com.zte.km.entities.OrderItem;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderItemMapper {

    //1.插入订单明细表
    void insert(@Param("orderItem") OrderItem orderItem);

}