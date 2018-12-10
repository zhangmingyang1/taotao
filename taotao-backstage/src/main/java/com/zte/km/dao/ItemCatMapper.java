package com.zte.km.dao;

import com.zte.km.entities.ItemCat;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemCatMapper {

    //1.获取商品分类列表
    List<ItemCat> getItemCatsByParentId(@Param("parentId") Long parentId);

    //2.根据id列表获取商品分类列表
    List<ItemCat> getItemCatsByIds(@Param("itemCatIdList") List<Long> itemCatIdList);
}