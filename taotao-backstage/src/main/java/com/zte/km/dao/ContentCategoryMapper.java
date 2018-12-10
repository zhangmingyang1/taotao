package com.zte.km.dao;

import com.zte.km.entities.ContentCategory;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentCategoryMapper {

    //1.获取内容分类列表
    List<ContentCategory> getContentCatList(@Param("parentId")Long parentId);

    //2.新增内容分类
    void createContentCat(@Param("contentCategory")ContentCategory contentCategory);

    //3.根据Id查询内容分类
    ContentCategory getContentCatById(@Param("id")Long id);

    //4.根据Id更新内容分类
    void updateContentCatById(@Param("contentCategory")ContentCategory contentCategory);

    //5.删除内容分类
    void deleteContentCatById(@Param("id")Long id);

}