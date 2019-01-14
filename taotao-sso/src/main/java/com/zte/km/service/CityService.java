package com.zte.km.service;

import com.zte.km.dao.CityMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    //1.获取省市区信息
    public ServiceData getCityMessage(Integer parentId) {
        ServiceData serviceData=new ServiceData();
        List<City> cityList = cityMapper.getCityMessage(parentId);
        serviceData.setStatus(200);
        serviceData.setData(cityList==null?new ArrayList<>():cityList);
        return serviceData;
    }

}
