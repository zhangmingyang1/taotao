package com.zte.km.controller;

import com.zte.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class PageController {
    @Autowired
    private UserService userService;

    /**
     * 登陆/注册页面跳转
     * @param page 跳转页面
     * @param redirect 重定向到之前页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/{page}",method = RequestMethod.GET)
    public String goPage(@PathVariable String page,String redirect, Model model){
        if (page==null || page.isEmpty()) {
            model.addAttribute("message","页面跳转路径错误!");
            return "error/exception";
        }
        model.addAttribute("redirect",redirect);
        return page;
    }
}
