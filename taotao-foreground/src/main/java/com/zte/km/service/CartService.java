package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.zte.km.common.utils.CookieUtils;
import com.zte.km.dto.CartItem;
import com.zte.km.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    //1.购物车列表
    public List<CartItem> getCartList() {
        return this.getCartItemList();
    }

    //2.添加商品至购物车，返回购物车列表
    public List<CartItem> addCart(Long itemId, Integer buyNum) {
        //获取购物车列表
        List<CartItem> cartItemList = this.getCartItemList();
        Optional<CartItem> optional = cartItemList != null ? cartItemList.stream().filter(x -> Objects.equals(x.getId(), itemId)).findFirst() : Optional.empty();
        if (optional.isPresent()){
            //购物车中存在此商品
            CartItem cartItem = optional.get();
            cartItem.setNum(cartItem.getNum()+buyNum);
        }else {
            //购物车中无此商品
            Item item = itemService.getItemById(itemId);
            CartItem cartItem=new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(buyNum);
            cartItem.setImage(item.getImage());
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            cartItemList.add(cartItem);    //商品加入购物车
        }
        //将购物车列表放入cookie
        CookieUtils.setCookie(request,response,"CART", JSON.toJSONString(cartItemList),true);
        return cartItemList;
    }

    //3.更新购物车商品数量，返回购物车列表
    public List<CartItem> updateCart(Long itemId, Integer num) {
        //获取购物车列表
        List<CartItem> cartItemList = this.getCartItemList();
        if (num==null || num==0)
            return cartItemList;
        Optional<CartItem> optional = cartItemList != null ? cartItemList.stream().filter(x -> Objects.equals(x.getId(), itemId)).findFirst() : Optional.empty();
        if (optional.isPresent()){
            //购物车中存在此商品
            CartItem cartItem = optional.get();
            cartItem.setNum(cartItem.getNum()+num);
        }
        //将购物车列表放入cookie
        CookieUtils.setCookie(request,response,"CART", JSON.toJSONString(cartItemList),true);
        return cartItemList;
    }

    //4.重新设置购买商品数量，返回购物车列表
    public List<CartItem> setCount(Long itemId, Integer num) {
        //获取购物车列表
        List<CartItem> cartItemList = this.getCartItemList();
        if (num==null || num==0)
            return cartItemList;
        Optional<CartItem> optional = cartItemList != null ? cartItemList.stream().filter(x -> Objects.equals(x.getId(), itemId)).findFirst() : Optional.empty();
        if (optional.isPresent()){
            //购物车中存在此商品
            CartItem cartItem = optional.get();
            cartItem.setNum(num);
        }
        //将购物车列表放入cookie
        CookieUtils.setCookie(request,response,"CART", JSON.toJSONString(cartItemList),true);
        return cartItemList;
    }

    //5.删除购买商品，返回购物车列表
    public List<CartItem> delete(Long itemId) {
        //获取购物车列表
        List<CartItem> cartItemList = this.getCartItemList();
        Optional<CartItem> optional = cartItemList != null ? cartItemList.stream().filter(x -> Objects.equals(x.getId(), itemId)).findFirst() : Optional.empty();
        if (optional.isPresent()){
            //购物车中存在此商品
            CartItem cartItem = optional.get();
            cartItemList.remove(cartItem);
        }
        //将购物车列表放入cookie
        CookieUtils.setCookie(request,response,"CART", JSON.toJSONString(cartItemList),true);
        return cartItemList;
    }

    //6.批量删除购买商品，返回购物车列表
    public List<CartItem> deleteItems(List<Long> itemIds) {
        List<CartItem> cartItemList = this.getCartItemList();
        if (cartItemList==null || cartItemList.isEmpty())
            return new ArrayList<>();
        itemIds.forEach(x -> {
            Optional<CartItem> optional = cartItemList.stream().filter(y -> Objects.equals(y.getId(), x)).findFirst();
            if (optional.isPresent()){
                //购物车中存在此商品
                CartItem cartItem = optional.get();
                cartItemList.remove(cartItem);
            }
        });
        //将购物车列表放入cookie
        CookieUtils.setCookie(request,response,"CART", JSON.toJSONString(cartItemList),true);
        return cartItemList;
    }

    //获取购物车列表
    private List<CartItem> getCartItemList() {
        String cartJson = CookieUtils.getCookieValue(request, "CART", true);
        if (cartJson==null || StringUtils.isEmpty(cartJson))
            return new ArrayList<>();
        return JSON.parseArray(cartJson,CartItem.class);
    }

}
