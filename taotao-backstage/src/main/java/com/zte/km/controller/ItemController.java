package com.zte.km.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.zte.km.dto.DataGridFormat;
import com.zte.km.dto.ItemUpdateReqDto;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.Item;
import com.zte.km.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/11/3.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //1.获取商品列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public String getItemList(@RequestParam(defaultValue = "1")Integer page,
                            @RequestParam(defaultValue = "30")Integer rows){
        DataGridFormat dataGridFormat=itemService.getItemList(page,rows);
        return JSON.toJSONString(dataGridFormat);
    }

    /**
     * 2.商品添加
     * @param item 商品信息
     * @param desc 商品描述信息
     * @param itemParams 商品规格参数信息
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveItem(Item item, String desc,String itemParams){
        ServiceData serviceData=new ServiceData();
        if (item==null){
            serviceData.setStatus(400);
            serviceData.setMessage("请求参数错误...");
        }else {
            serviceData=itemService.saveItem(item,desc,itemParams);
        }
        return JSON.toJSONString(serviceData);
    }

    //3.删除商品
    /**
     * 3.删除商品
     * @param ids 要删除的商品Id列表
     * @return 删除结果(需指定返回值类型application/json)
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteItemByIds(@RequestParam List<Long> ids){
        if (ids==null||ids.isEmpty()){
            return JSON.toJSONString(new ServiceData<>(200));
        }
        ServiceData serviceData=itemService.deleteItemByIds(ids);
        return JSON.toJSONString(serviceData);
    }

    //4.商品上架
    @RequestMapping(value = "/shelf",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shelf(@RequestParam List<Long> ids){
        if (ids==null||ids.isEmpty()){
            return JSON.toJSONString(new ServiceData<>(200));
        }
        ServiceData serviceData=itemService.shelfItemByIds(ids);
        return JSON.toJSONString(serviceData);
    }

    //5.商品下架
    @RequestMapping(value = "/shelve",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shelve(@RequestParam List<Long> ids){
        if (ids == null||ids.isEmpty()){
            return JSON.toJSONString(new ServiceData<>(200));
        }
        ServiceData serviceData=itemService.shelveItemByIds(ids);
        return JSON.toJSONString(serviceData);
    }

    //6.商品更新
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(ItemUpdateReqDto itemUpdateReqDto){
        if (itemUpdateReqDto == null||itemUpdateReqDto.getId()==null){
            return JSON.toJSONString(new ServiceData<>(400));
        }
        ServiceData serviceData=itemService.update(itemUpdateReqDto);
        return JSON.toJSONString(serviceData);
    }

    /**
     * 发布服务：7.根据商品ID查询商品信息
     * @param itemId 商品ID
     * @return 商品信息
     */
    @RequestMapping(value = "/getItemInfo/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData getItemInfo(@PathVariable Long itemId) {
        //--查询条件不能为空
        if (itemId == null) {
            return new ServiceData(400, "商品ID不能为空.");
        }
        ServiceData serviceData = null;
        try {
            serviceData = itemService.getItemById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceData(500, e.getMessage());
        }
        return serviceData;
    }


}
