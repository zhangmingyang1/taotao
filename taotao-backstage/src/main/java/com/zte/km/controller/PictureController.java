package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.UploadResult;
import com.zte.km.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 接收页面传递过来的图片,调用service上传到图片服务器.
 * Created by Administrator on 2018/11/4.
 */
@Controller
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //1.图片上传至FTP
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public  UploadResult upload(@RequestParam("uploadFile") MultipartFile multipartFile) {
        UploadResult response = new UploadResult();
        if (multipartFile == null || multipartFile.isEmpty()) {
            response.setError(1);
            response.setMessage("上传图片为空!");
        } else {
            response = pictureService.uploadPicture(multipartFile);
        }
        return response;
    }

}
