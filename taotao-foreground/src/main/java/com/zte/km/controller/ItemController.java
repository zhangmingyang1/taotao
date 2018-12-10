package com.zte.km.controller;

import com.zte.km.dto.Item;
import com.zte.km.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //1.根据商品ID查询商品信息
    @RequestMapping(value = "/getItemInfo/{itemId}",method = RequestMethod.GET)
    public String getItemInfo(@PathVariable Long itemId, Model model){
        if (itemId != null && itemId >= 0 ){
            Item item = itemService.getItemById(itemId);
            model.addAttribute("item", item);
        }
        return "item";
    }


}
