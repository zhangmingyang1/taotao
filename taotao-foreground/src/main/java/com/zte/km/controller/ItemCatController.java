package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ItemCatData;
import com.zte.km.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/11/7.
 */
@Controller
@RequestMapping("/itemCat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    //1.获取商品分类列表
    @RequestMapping(value = "/getItemCatList",method = RequestMethod.GET)
    @ResponseBody
    public String getItemCatList(){
        ItemCatData itemCatData=itemCatService.getItemCatList();
        return JSON.toJSONString(itemCatData);
    }

}
