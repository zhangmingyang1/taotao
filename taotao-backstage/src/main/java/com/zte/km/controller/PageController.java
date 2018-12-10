package com.zte.km.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2018/11/3.
 */
@Controller
public class PageController {

    //1.后台页面跳转
    @RequestMapping(value = "/{url}",method = RequestMethod.GET)
    public String goPage(@PathVariable String url){
        return url;
    }

}
