package com.zte.km.controller;

import com.zte.km.dto.ServiceData;
import com.zte.km.entities.User;
import com.zte.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //1.检查用户登陆信息
    @RequestMapping(value = "/check/{content}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData check(@PathVariable String content,@PathVariable Integer type){
        //type=1,2,3分别代表username，phone，email
        if (type != 1 && type != 2 && type != 3)
            return new ServiceData(400,"校验内容类型错误！");
        String decode =null;
        try {
            decode=URLDecoder.decode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userService.check(decode,type);
    }

    //2.用户注册
    @RequestMapping(value = "/goRegister",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData register(User user){
        if (user==null || user.getUsername().isEmpty())
            return new ServiceData(400,"请求参数错误！");
        return userService.register(user);
    }

    //3.用户登陆
    @RequestMapping(value = "/goLogin",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData login(String username, String password, Model model){
        if (username==null || username.isEmpty())
            return new ServiceData(400,"用户名为空！");
        if (password==null || password.isEmpty())
            return new ServiceData(400,"密码为空！");
        return userService.login(username,password);
    }

    //4.通过token检查用户信息
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData token(@PathVariable String token){
        if (token==null || token.isEmpty())
            return new ServiceData(400,"token为空！");
        return userService.token(token);
    }

}
