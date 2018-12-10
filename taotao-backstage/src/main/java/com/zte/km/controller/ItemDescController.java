package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ServiceData;
import com.zte.km.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/11/3.
 */
@Controller
@RequestMapping("/itemDesc")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    //1.根据id查询商品描述
    @RequestMapping(value = "/getItemDescInfo/{itemId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getItemDescById(@PathVariable Long itemId){
        if (itemId == null){
            return "400";
        }
        ServiceData serviceData=itemDescService.getItemDescById(itemId);
        return JSON.toJSONString(serviceData);
    }

}
