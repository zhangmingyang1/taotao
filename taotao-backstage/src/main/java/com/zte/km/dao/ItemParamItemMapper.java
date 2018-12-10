package com.zte.km.dao;

import com.zte.km.entities.ItemParamItem;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemParamItemMapper {

    //1.商品规格参数内容
    void insert(@Param("itemParamItem") ItemParamItem itemParamItem);

    //2.根据itemId查询商品规格参数数据
    ItemParamItem getItemParamItem(@Param("itemId") Long itemId);

    //3.更新商品規格內容
    boolean update(@Param("itemParamItem") ItemParamItem itemParamItem);

}