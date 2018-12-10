package com.zte.km.service;

import com.zte.km.dao.ItemDescMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/11/3.
 */
@Service
public class ItemDescService {

    @Autowired
    private ItemDescMapper itemDescMapper;

    //1.根据id查询商品描述
    public ServiceData getItemDescById(Long itemId) {
        ServiceData<ItemDesc> serviceData=new ServiceData<>();
        //根据ID上架商品
        ItemDesc itemDesc=itemDescMapper.getItemDescById(itemId);
        serviceData.setStatus(200);
        serviceData.setData(itemDesc);
        return serviceData;
    }
}
