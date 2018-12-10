package com.zte.km.service;

import com.zte.km.dao.ContentMapper;
import com.zte.km.dto.DataGridFormat;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.Content;
import com.zte.km.entities.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    //1.查询内容列表
    public DataGridFormat getContentList(Long categoryId, Integer page, Integer rows) {
        DataGridFormat dataGridFormat=new DataGridFormat();
        //1.查询分类下，分类总记录数
        Long count=contentMapper.getCategoryCount(categoryId);
        //2.查询内容列表
        List<Content> contentList=contentMapper.getContentList(categoryId, (page-1)*rows, rows);
        dataGridFormat.setTotal(count);
        dataGridFormat.setRows(contentList);
        return dataGridFormat;
    }

    //2.新增内容
    public ServiceData addContent(Content content) {
        ServiceData serviceData=new ServiceData();
        boolean success=contentMapper.addContent(content);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("新增内容成功!");
        }
        return serviceData;
    }

    //3.编辑内容
    public ServiceData editContent(Content content) {
        ServiceData serviceData=new ServiceData();
        boolean success=contentMapper.editContent(content);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("编辑内容成功!");
        }
        return serviceData;
    }

    //4.删除内容列表
    public ServiceData deleteContentByIds(List<Long> ids) {
        ServiceData serviceData=new ServiceData();
        boolean success=contentMapper.deleteContentByIds(ids);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("删除内容列表成功!");
        }
        return serviceData;
    }

    //1.1.发布微服务，前台广告位展示
    public ServiceData getContentListByCid(Long contentCategoryId) {
        ServiceData serviceData=new ServiceData();
        List<Content> contentList = contentMapper.getContentList(contentCategoryId, null, null);
        serviceData.setStatus(200);
        serviceData.setData(contentList);
        return serviceData;
    }

}
