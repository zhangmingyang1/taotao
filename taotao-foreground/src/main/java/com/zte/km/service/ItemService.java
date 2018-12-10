package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.Item;
import com.zte.km.dto.SearchData;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

@Service
public class ItemService {

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Autowired
    private CacheManagerService cacheManagerService;

    //1.根据商品ID查询商品信息
    public Item getItemById(Long itemId) {
        return cacheManagerService.getAndSet("ITEM_INFO", new Callable<Item>() {
            @Override
            public Item call() throws Exception {
                //调用rest的服务查询商品基本信息
                String response = HttpClientUtil.doGet(ITEM_INFO_URL + itemId);
                if (response==null || response.isEmpty())
                    return null;
                ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                if (serviceData != null && serviceData.getStatus() == 200 && serviceData.getData()!=null) {
                    JSONObject jsonObject = (JSONObject) serviceData.getData();
                    return jsonObject.toJavaObject(Item.class);
                }
                return null;
            }
        },itemId);
    }

}
