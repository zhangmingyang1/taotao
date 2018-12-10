package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.TreeNode;
import com.zte.km.service.ContentCategoryService;
import groovyjarjarantlr.collections.impl.LList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    //1.获取内容分类列表
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    @ResponseBody
    public String getContentCatList(@RequestParam(value="id", defaultValue="0") Long parentId){
        List<TreeNode> contentCatList=contentCategoryService.getContentCatList(parentId);
        return JSON.toJSONString(contentCatList);
    }

    //2.新增内容分类
    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createContentCat(Long parentId, String name) {
        ServiceData serviceData = contentCategoryService.createContentCat(parentId, name);
        return JSON.toJSONString(serviceData);
    }

    //3.更新内容分类
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String updateContentCat(Long id, String name) {
        ServiceData serviceData = contentCategoryService.updateContentCat(id, name);
        return JSON.toJSONString(serviceData);
    }

    //4.删除内容分类
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteContentCat(Long id) {
        ServiceData serviceData = contentCategoryService.deleteContentCat(id);
        return JSON.toJSONString(serviceData);
    }

}
