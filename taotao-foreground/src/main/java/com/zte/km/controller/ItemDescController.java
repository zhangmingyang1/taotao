package com.zte.km.controller;

import com.zte.km.dto.Item;
import com.zte.km.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemDesc")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    //1.根据商品ID查询商品描述信息
    @RequestMapping(value = "/getItemDescInfo/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public String getItemDescInfo(@PathVariable Long itemId){
        String response=null;
        if (itemId != null && itemId >= 0 ){
            response = itemDescService.getItemDescById(itemId);
        }
        return response;
    }

}
