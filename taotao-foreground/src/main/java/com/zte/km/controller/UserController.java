package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.*;
import com.zte.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //1.token查询用户信息
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData token(@PathVariable String token){
        if (token==null || token.isEmpty())
            return new ServiceData(400,"token为空！");
        return userService.token(token);
    }

    //2.用户安全退出
    @RequestMapping(value = "/logout/{token}",method = RequestMethod.GET)
    public String logout(@PathVariable String token, HttpServletResponse response) throws IOException {
        if (token != null && !token.isEmpty())
            userService.logout(token);
        //跳转到网站首页
        return "index";
    }

    //3.页面跳转
    @RequestMapping(value = "/{page}",method = RequestMethod.GET)
    public String userMessage(@PathVariable String page){
        return page;
    }


    //4.获取省市区信息
    @RequestMapping(value = "/getCityMessage/{parentId}",method = RequestMethod.GET)
    @ResponseBody
    public List<City> getCityMessage(@PathVariable Integer parentId){
        if (parentId == null || parentId < 0)
            return new ArrayList<>();
        return userService.getCityMessage(parentId);
    }

    //5.用户基本信息更新
    @RequestMapping(value = "/user_Message_update",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData userMessageUpdate(@RequestBody UserMessage userMessage){
        if (userMessage==null)
            return new ServiceData(400,"请求参数错误...");
        return userService.userMessageUpdate(userMessage);
    }

    //6.用户头像更新
    @RequestMapping(value = "/user_uploadImage",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData uploadImage(MultipartFile file,String src) throws IOException {
        if (file == null && src == null)
            return new ServiceData(400,"请求参数错误...");
        String ext = null;
        InputStream inputStream = null;
        if (src == null || src.isEmpty()){
            //1.用户头像为自己上传图片
            String originalFilename = file.getOriginalFilename();
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            inputStream = file.getInputStream();
        }else {
            //2.用户头像为默认图片
            ext =src.substring(src.lastIndexOf("."));
            String filePath = this.getClass().getResource("/").getPath() + "static" + src;
            inputStream = new FileInputStream(this.getClass().getResource("/").getPath()+"static"+src);
        }
        return userService.uploadImage(ext,inputStream);
    }

    //7.用户收货地址更新
    @RequestMapping(value = "/user_address_update",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData userExtendUpdate(@RequestBody List<UserMessageAddress> userMessageAddressList){
        if (userMessageAddressList==null || userMessageAddressList.isEmpty())
            return new ServiceData(400,"请求参数错误...");
        return userService.userAddressUpdate(userMessageAddressList);
    }

}
