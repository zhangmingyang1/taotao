package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class UserService {

    @Value("${SSO_USER_URL}")
    private String SSO_USER_URL;

    public ServiceData token(String token) {
        String json = HttpClientUtil.doGet(SSO_USER_URL + token);
        ServiceData serviceData = JSON.parseObject(json, ServiceData.class);
        if (serviceData!=null && serviceData.getStatus()==200){
            return serviceData;
        }
        return null;
    }
}
