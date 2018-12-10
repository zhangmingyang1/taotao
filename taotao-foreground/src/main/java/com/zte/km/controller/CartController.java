package com.zte.km.controller;

import com.zte.km.dto.CartItem;
import com.zte.km.dto.ServiceData;
import com.zte.km.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //1.购物车列表
    @RequestMapping("/cartList")
    public String cartList(Model model){
        List<CartItem> cartList= cartService.getCartList();
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    //2.添加商品至购物车，返回购物车列表
    @RequestMapping("/add/{itemId}")
    public String add(@PathVariable Long itemId,@RequestParam(defaultValue = "1")Integer buyNum, Model model){
        if (itemId != null && itemId >= 0){
            List<CartItem> cartList= cartService.addCart(itemId,buyNum);
            model.addAttribute("cartList",cartList);
        }
        return "cart";
    }

    //3.更新购物车商品数量，返回购物车列表
    @RequestMapping("/update/{itemId}/{num}")
    public String update(@PathVariable Long itemId,@PathVariable Integer num, Model model){
        List<CartItem> cartList= cartService.updateCart(itemId,num);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    //4.重新设置购买商品数量，返回购物车列表
    @RequestMapping("/setCount/{itemId}")
    public String setCount(@PathVariable Long itemId,@RequestParam(defaultValue = "1") Integer num, Model model){
        List<CartItem> cartList= cartService.setCount(itemId,num);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    //5.删除购买商品，返回购物车列表
    @RequestMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId, Model model){
        if (itemId != null && itemId >= 0){
            List<CartItem> cartList= cartService.delete(itemId);
            model.addAttribute("cartList",cartList);
        }
        return "cart";
    }

    //6.批量删除购买商品，返回购物车列表
    @RequestMapping(value = "/deleteItems",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData deleteItems(@RequestParam List<Long> itemIds){
        ServiceData serviceData=new ServiceData();
        if (itemIds != null && itemIds.size() >= 0){
            List<CartItem> cartList= cartService.deleteItems(itemIds);
            serviceData.setStatus(200);
            serviceData.setData(cartList);
        }
        return serviceData;
    }

}
