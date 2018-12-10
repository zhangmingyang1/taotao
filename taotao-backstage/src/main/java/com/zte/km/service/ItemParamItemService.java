package com.zte.km.service;

import com.zte.km.dao.ItemParamItemMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/11/6.
 */
@Service
public class ItemParamItemService {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    //1.根据itemId查询商品规格参数数据
    public ServiceData getItemParamItem(Long itemId) {
        ServiceData<ItemParamItem> serviceData=new ServiceData<>();
        ItemParamItem itemParamItem=itemParamItemMapper.getItemParamItem(itemId);
        serviceData.setStatus(200);
        serviceData.setData(itemParamItem);
        return serviceData;
    }

}
