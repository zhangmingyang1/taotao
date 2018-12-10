package com.zte.km.dao;

import com.zte.km.entities.OrderShipping;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderShippingMapper {

    //1.插入订单物流表
    void insert(@Param("orderShipping") OrderShipping orderShipping);
    
}