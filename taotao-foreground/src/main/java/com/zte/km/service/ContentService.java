package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.Content;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Value("${HOME_AD_CATID}")
    private String HOME_AD_CATID;    //大广告位分类ID

    @Value("${HOME_AD_URL}")
    private String HOME_AD_URL;      //请求微服务URL

    @Autowired
    private CacheManagerService cacheManagerService;

    //1.调用微服务，加载首页大广告
    public String getHomeAdvertise() {
        return cacheManagerService.getAndSet("HOME_CONTENT", new Callable<String>(){
            @Override
            public String call() throws Exception {
                String response = HttpClientUtil.doGet(HOME_AD_URL);
                if (response != null && response.length()!=0){
                    ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                    if (serviceData != null && serviceData.getStatus() == 200) {
                        List<JSONObject> list = (List<JSONObject>) serviceData.getData();
                        List<Content> contentList = list.stream().map(x -> x.toJavaObject(Content.class)).collect(Collectors.toList());
                        List<Map> adList = new ArrayList<>();
                        //创建HTML页面要求的pojo列表
                        for (Content content : contentList) {
                            Map map = new HashMap<>();
                            map.put("srcB", content.getPic2());
                            map.put("height", 240);
                            map.put("alt", content.getSubTitle());
                            map.put("width", 670);
                            map.put("src", content.getPic());
                            map.put("widthB", 550);
                            map.put("href", content.getUrl());
                            map.put("heightB", 240);
                            adList.add(map);
                        }
                        return JSON.toJSONString(adList);
                    }
                }
                return null;
            }
        },HOME_AD_CATID);
    }
}
