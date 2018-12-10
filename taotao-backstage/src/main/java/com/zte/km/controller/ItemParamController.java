package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.ItemParam;
import com.zte.km.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品规格参数模板
 * Created by Administrator on 2018/11/5.
 */
@Controller
@RequestMapping("/itemParam")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    //1.查询商品规格参数
    @RequestMapping(value = "/query/{itemCatId}",method = RequestMethod.GET)
    @ResponseBody
    public String getItemParam(@PathVariable Long itemCatId){
        if (itemCatId==null)
            return null;
        ServiceData serviceData=itemParamService.getItemParamByCid(itemCatId);
        return JSON.toJSONString(serviceData);
    }

    //2.插入商品规格参数
    @RequestMapping(value = "/save/{cid}",method = RequestMethod.POST)
    @ResponseBody
    public String insertItemParam(String paramData, @PathVariable Long cid) {
        if (paramData==null||cid==null)
            return null;
        ServiceData serviceData=itemParamService.insertItemParam(paramData,cid);
        return JSON.toJSONString(serviceData);
    }

    //3.查询商品规格参数列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public String getItemParamList() {
        List<ItemParam> itemParamList = itemParamService.getItemParamList();
        return JSON.toJSONString(itemParamList);
    }

}
