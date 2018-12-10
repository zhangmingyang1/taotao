package com.zte.km.service;

import com.zte.km.dao.ItemParamMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.ItemCat;
import com.zte.km.entities.ItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/11/5.
 */
@Service
public class ItemParamService {

    @Autowired
    private ItemParamMapper itemParamMapper;
    @Autowired
    private ItemCatService itemCatService;

    public ServiceData getItemParamByCid(Long itemCatId) {
        ServiceData<ItemParam> serviceData=new ServiceData<>();
        //1.根据商品分类ID查询商品规格参数模板
        ItemParam itemParam=itemParamMapper.getItemParamByCid(itemCatId);
        serviceData.setStatus(200);
        serviceData.setMessage("查询成功.");
        serviceData.setData(itemParam);
        return serviceData;
    }

    //2.插入商品规格参数
    public ServiceData insertItemParam(String paramData, Long cid) {
        ServiceData serviceData=new ServiceData();
        //创建pojo对象
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        //插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        serviceData.setStatus(200);
        return serviceData;
    }

    //3.查询商品规格参数列表
    public List<ItemParam> getItemParamList() {
        List<ItemParam> itemParamList = itemParamMapper.getItemParamList();
        //获取所有商品分类ID列表
        List<Long> itemCatIdList = itemParamList.stream().map(ItemParam::getItemCatId).collect(Collectors.toList());
        List<ItemCat> itemCatList=itemCatService.getItemCatsByIds(itemCatIdList);
        //所有商品规格参数设置分类名称
        itemParamList.forEach(x -> {
            ItemCat itemCat = itemCatList.stream().filter(y -> y.getId().equals(x.getItemCatId())).findFirst().get();
            x.setItemCatName(itemCat.getName());
        });
        return itemParamList;
    }
}
