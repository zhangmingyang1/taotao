package com.zte.km.dao;

import com.zte.km.entities.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {

    //1.获取省市区信息
    List<City> getCityMessage(@Param("parentId") Integer parentId);

}