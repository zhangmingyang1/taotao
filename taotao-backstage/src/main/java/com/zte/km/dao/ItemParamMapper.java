package com.zte.km.dao;

import com.zte.km.entities.ItemParam;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemParamMapper {

    //1.根据商品分类ID查询商品规格参数模板
    ItemParam getItemParamByCid(@Param("itemCatId") Long itemCatId);

    //2.插入商品规格参数
    void insert(@Param("itemParam") ItemParam itemParam);

    //3.查询商品规格参数列表
    List<ItemParam> getItemParamList();

}