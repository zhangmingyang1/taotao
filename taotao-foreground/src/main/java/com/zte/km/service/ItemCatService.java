package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.ItemCatData;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/11/7.
 */
@Service
public class ItemCatService {

    @Value("${GET_ITEMCAT_URL}")
    private String GET_ITEMCAT_URL;

    @Autowired
    private CacheManagerService cacheManagerService;

    //1.获取商品分类列表
    public ItemCatData getItemCatList() {
        return  cacheManagerService.getAndSet("ITEM_CATEGORY_LIST", new Callable<ItemCatData>() {
            @Override
            public ItemCatData call() throws Exception {
                ItemCatData itemCatData = new ItemCatData();
                String response = HttpClientUtil.doGet(GET_ITEMCAT_URL);
                if (response == null || response.isEmpty())
                    return itemCatData;
                ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                if (serviceData != null && serviceData.getStatus() == 200) {
                    List<?> ItemCatNodeList = (List<?>) serviceData.getData();
                    itemCatData.setData(ItemCatNodeList);
                }
                return itemCatData;
            }
        }, null);
    }


}
