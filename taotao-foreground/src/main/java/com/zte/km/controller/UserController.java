package com.zte.km.controller;

import com.zte.km.dto.ServiceData;
import com.zte.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData token(@PathVariable String token){
        if (token==null || token.isEmpty())
            return new ServiceData(400,"token为空！");
        return userService.token(token);
    }

}
