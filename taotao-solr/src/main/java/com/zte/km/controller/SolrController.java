package com.zte.km.controller;

import com.zte.km.dto.ItemMessage;
import com.zte.km.dto.ServiceData;
import com.zte.km.service.SolrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private SolrService solrService;

    /**
     * 发布服务：1.查询商品信息
     * @param key 查询条件
     * @param page 当前第几页
     * @param rows 每页显示记录数
     * @return 商品列表
     */
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData inquire(@RequestParam("key")String key,
                              @RequestParam(defaultValue="1")Integer page,
                              @RequestParam(defaultValue="60")Integer rows) {
        //--查询条件不能为空
        if (StringUtils.isEmpty(key)) {
            return new ServiceData(400, "查询条件不能为空.");
        }
        ServiceData serviceData = null;
        try {
            serviceData = solrService.inquire(key,page,rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceData(500, e.getMessage());
        }
        return serviceData;
    }

    /**
     * 发布服务：2.新增/更新商品信息
     * @param itemMessage 商品信息
     * @return 新增/更新结果
     */
    @RequestMapping(value = "/addOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public ServiceData addOrUpdate(ItemMessage itemMessage) {
        if (itemMessage==null)
            return new ServiceData(400,"请求参数错误.");
        return  solrService.addOrUpdate(itemMessage);
    }

    /**
     * 发布服务：3.根据id删除索引
     * @param id 商品ID
     * @return 删除结果
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData delete(String id)  {
        if (id==null || id.isEmpty())
            return new ServiceData<>(200,"ok");
        return solrService.delete(id);
    }

    /**
     * 发布服务：4.删除所有索引库数据
     * @return 删除结果
     */
    @RequestMapping(value = "/delete/overall",method = RequestMethod.GET)
    @ResponseBody
    public ServiceData overall()  {
        return solrService.overall();
    }

}
