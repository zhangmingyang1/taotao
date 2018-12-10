package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.SearchData;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SolrService {

    @Value("${SOLR_SEARCH_URL}")
    private String SOLR_SEARCH_URL;

    public SearchData query(String key, Integer page) {
        SearchData searchData=new SearchData();
        //查询参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("page", String.valueOf(page));
        paramMap.put("rows", String.valueOf(60));
        String response=HttpClientUtil.doGet(SOLR_SEARCH_URL,paramMap);
        if (response == null || response.isEmpty())
            return searchData;
        ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
        if (serviceData != null && serviceData.getStatus() == 200) {
            JSONObject jsonObject = (JSONObject) serviceData.getData();
            searchData = jsonObject.toJavaObject(SearchData.class);
        }
        return searchData;
    }

}
