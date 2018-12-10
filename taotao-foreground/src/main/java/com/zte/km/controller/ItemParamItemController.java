package com.zte.km.controller;

import com.zte.km.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemParamItem")
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    //1.根据商品ID查询商品规格参数
    @RequestMapping(value = "/getItemParamItemInfo/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public String getItemParamItemInfo(@PathVariable Long itemId) {
        String response=null;
        if (itemId != null && itemId >= 0 ){
            response = itemParamItemService.getItemParamItemById(itemId);
        }
        return response;
    }


}
