package com.zte.km.dao;

import com.zte.km.entities.Item;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemMapper {

    //1.获取商品列表
    List<Item> getItemList(@Param("start") Integer start,
                           @Param("rows") Integer rows);
    //2.查询商品总数
    Long getItemCount();

    //3.根据ids删除商品
    boolean deleteItemByIds(@Param("ids")List<Long> ids);

    //4.根据IDs上架商品
    boolean shelfItemByIds(@Param("ids")List<Long> ids);

    //5.根据IDs下架商品
    boolean shelveItemByIds(@Param("ids")List<Long> ids);

    //6.插入商品数据
    void insert(@Param("item")Item item);

    //7.更新商品表
    boolean update(@Param("item") Item item);

    //8.根据ID查询商品信息
    Item getItemById(@Param("itemId") Long itemId);

}