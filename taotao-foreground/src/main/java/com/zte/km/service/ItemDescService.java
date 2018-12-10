package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.Item;
import com.zte.km.dto.ItemDesc;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class ItemDescService {

    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;

    @Autowired
    private CacheManagerService cacheManagerService;

    //1.根据商品ID查询商品描述信息
    public String getItemDescById(Long itemId) {
        return cacheManagerService.getAndSet("ITEM_DESC", new Callable<String>() {
            @Override
            public String call() throws Exception {
                //调用微服务，查询商品描述
                String response = HttpClientUtil.doGet(ITEM_DESC_URL + itemId);
                if (response==null || response.isEmpty())
                    return null;
                ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                if (serviceData != null && serviceData.getStatus() == 200 && serviceData.getData()!=null) {
                    JSONObject jsonObject = (JSONObject) serviceData.getData();
                    ItemDesc itemDesc = jsonObject.toJavaObject(ItemDesc.class);
                    return itemDesc.getItemDesc();
                }
                return null;
            }
        },itemId);
    }


}
