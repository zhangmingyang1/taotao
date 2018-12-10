package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ServiceData;
import com.zte.km.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品规格参数
 * Created by Administrator on 2018/11/6.
 */
@Controller
@RequestMapping("/itemParamItem")
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    //1.根据itemId查询商品规格参数数据
    @RequestMapping(value = "/getItemParamItemInfo/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public String getItemParamItem(@PathVariable Long itemId){
        if (itemId == null)
            return null;
        ServiceData serviceData=itemParamItemService.getItemParamItem(itemId);
        return JSON.toJSONString(serviceData);
    }

}
