package com.zte.km.common.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component(value = "memCacheKeys")
public class MemCacheKeys {
    private Map<String,CacheEntity> caches=new HashMap<>();

    public MemCacheKeys() {
        //1.前台商品分类列表
        caches.put("ITEM_CATEGORY_LIST",new CacheEntity("com:taotao:itemCatList",10080));
        //2.前台广告位
        caches.put("HOME_CONTENT",new CacheEntity("com:taotao::HomeContentList:%s",10080));
        //3.商品信息
        caches.put("ITEM_INFO",new CacheEntity("com:taotao:itemMessage:%s",1, TimeUnit.DAYS));
        //4.商品描述
        caches.put("ITEM_DESC",new CacheEntity("com:taotao:itemDesc:%s",1, TimeUnit.DAYS));
        //5.商品规格参数
        caches.put("ITEM_PARAM",new CacheEntity("com:taotao:itemParam:%s",1, TimeUnit.DAYS));
        //6.省市区信息(永不过期)
        caches.put("PROVINCE_CITY_COUNTY",new CacheEntity("com:taotao:city:%s",-1));

    }

    public CacheEntity getCacheEntity(String cacheKey){
        return caches.get(cacheKey);
    }

}
