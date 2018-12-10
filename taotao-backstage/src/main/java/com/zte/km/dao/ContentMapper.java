package com.zte.km.dao;

import com.zte.km.entities.Content;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentMapper {

    //1.查询分类下，分类总记录数
    Long getCategoryCount(@Param("categoryId")Long categoryId);

    //2.查询内容列表
    List<Content> getContentList(@Param("categoryId") Long categoryId,
                                 @Param("start") Integer start,
                                 @Param("rows") Integer rows);

    //3.新增内容
    boolean addContent(@Param("content") Content content);

    //4.编辑内容
    boolean editContent(@Param("content")Content content);

    //5.删除内容列表
    boolean deleteContentByIds(@Param("ids")List<Long> ids);

}