package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.DataGridFormat;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.Content;
import com.zte.km.entities.ContentCategory;
import com.zte.km.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    //1.查询内容列表
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    @ResponseBody
    public String getContentList(@RequestParam(defaultValue="0") Long categoryId,
                                 @RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "30")Integer rows){
        DataGridFormat dataGridFormat=contentService.getContentList(categoryId,page,rows);
        return JSON.toJSONString(dataGridFormat);
    }

    //1.1.发布微服务，前台广告位展示
    @RequestMapping(value = "/getList/{contentCategoryId}",method = RequestMethod.GET)
    @ResponseBody
    public String getContentList(@PathVariable Long contentCategoryId){
        ServiceData serviceData = contentService.getContentListByCid(contentCategoryId);
        return JSON.toJSONString(serviceData);
    }

    //2.新增内容
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addContent(Content content){
        ServiceData serviceData=contentService.addContent(content);
        return JSON.toJSONString(serviceData);
    }

    //3.编辑内容
    @RequestMapping(value = "/edit",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editContent(Content content){
        ServiceData serviceData=contentService.editContent(content);
        return JSON.toJSONString(serviceData);
    }

    //4.删除内容列表
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteContent(@RequestParam List<Long> ids){
        if (ids==null||ids.isEmpty()){
            return JSON.toJSONString(new ServiceData<>(200));
        }
        ServiceData serviceData=contentService.deleteContentByIds(ids);
        return JSON.toJSONString(serviceData);
    }

}
