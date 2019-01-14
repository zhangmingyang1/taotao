package com.zte.km.controller;

import com.zte.km.dto.ServiceData;
import com.zte.km.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 维护省市区信息
 */
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    //1.获取省市区信息
    @RequestMapping(value = "/getCityMessage/{parentId}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData getCityMessage(@PathVariable Integer parentId){
        if (parentId == null || parentId < 0)
            return new ServiceData(400,"请求参数错误...");
        return cityService.getCityMessage(parentId);
    }

}
