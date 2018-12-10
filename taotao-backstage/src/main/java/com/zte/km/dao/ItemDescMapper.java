package com.zte.km.dao;

import com.zte.km.entities.ItemDesc;

import org.apache.ibatis.annotations.Param;

public interface ItemDescMapper {

    //1.根据id查询商品描述
    ItemDesc getItemDescById(@Param("itemId") Long itemId);

    //2.插入商品描述
    void insert(@Param("itemDesc") ItemDesc itemDesc);

    //3.更新商品描述
    boolean update(@Param("itemDesc")ItemDesc itemDesc);

}