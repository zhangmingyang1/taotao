package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/")
    public String goHome(Model model){
        //跳转至主页时，调用微服务，加载广告位
        String adJson=contentService.getHomeAdvertise();
        //此处有坑，不可直接返回json字符串，需返回JSONObject/JSONArray
        model.addAttribute("HOME_AD", JSON.parseArray(adJson));
        return "index";
    }


}
