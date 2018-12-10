package com.zte.km.controller;

import com.alibaba.fastjson.JSON;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.TreeNode;
import com.zte.km.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4.
 */
@Controller
@RequestMapping("/itemCat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 1.根据parentId获取商品分类列表
     * @param parentId 父分类ID
     * @return 商品分类列表
     */
    @RequestMapping(value = "/getListByPId",method = RequestMethod.POST)
    @ResponseBody
    public String getItemCatsByParentId(@RequestParam(value="id", defaultValue="0")Long parentId){
        List<TreeNode> list = itemCatService.getItemCatsByParentId(parentId);
        return JSON.toJSONString(list);
    }

    //2.获取所有商品分类列表
    @RequestMapping(value = "/getItemCatList",method = RequestMethod.GET)
    @ResponseBody
    public String getItemCatList(){
        ServiceData serviceData = itemCatService.getItemCatList();
        return JSON.toJSONString(serviceData);
    }

}
