package com.zte.km.service;

import com.zte.km.dao.ItemDescMapper;
import com.zte.km.dao.ItemMapper;
import com.zte.km.dao.ItemParamItemMapper;
import com.zte.km.dto.DataGridFormat;
import com.zte.km.dto.ItemUpdateReqDto;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.Item;
import com.zte.km.entities.ItemDesc;
import com.zte.km.entities.ItemParam;
import com.zte.km.entities.ItemParamItem;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/11/3.
 */
@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    //1.获取商品列表
    public DataGridFormat getItemList(Integer page, Integer rows) {
        //1.查询商品
        List<Item> itemList=itemMapper.getItemList((page-1)*rows,rows);
        //2.查询商品总数
        Long count=itemMapper.getItemCount();
        //Easyui中datagrid控件要求的数据格式
        DataGridFormat dataGridFormat=new DataGridFormat();
        dataGridFormat.setTotal(count);
        dataGridFormat.setRows(itemList);
        return dataGridFormat;
    }

    //2.商品添加
    public ServiceData saveItem(Item item, String desc,String itemParams) {
        ServiceData serviceData=new ServiceData();
        //生成商品id
        //可以使用redis的自增长key，在没有redis之前使用时间+随机数策略生成
        Long itemId = System.currentTimeMillis();
        //补全不完整的字段
        item.setId(itemId);
        item.setStatus(1);
        //1.数据插入到商品表
        itemMapper.insert(item);

        //2.添加商品描述
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        //把数据插入到商品描述表
        itemDescMapper.insert(itemDesc);

        //3.商品规格参数内容
        ItemParamItem itemParamItem=new ItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItemMapper.insert(itemParamItem);

        serviceData.setStatus(200);
        serviceData.setMessage("商品添加成功.");
        return serviceData;
    }
    //3.删除商品
    public ServiceData deleteItemByIds(List<Long> ids) {
        ServiceData serviceData=new ServiceData();
        //根据IDs删除商品
        boolean success=itemMapper.deleteItemByIds(ids);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("删除商品成功!");
        }
        return serviceData;
    }

    //4.根据IDs上架商品
    public ServiceData shelfItemByIds(List<Long> ids) {
        ServiceData serviceData=new ServiceData();
        //根据IDs上架商品
        boolean success=itemMapper.shelfItemByIds(ids);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("商品上架成功!");
        }
        return serviceData;
    }

    //5.根据IDs下架商品
    public ServiceData shelveItemByIds(List<Long> ids) {
        ServiceData serviceData=new ServiceData();
        //根据IDs上架商品
        boolean success=itemMapper.shelveItemByIds(ids);
        if(success){
            serviceData.setStatus(200);
            serviceData.setMessage("商品下架成功!");
        }
        return serviceData;
    }

    //6.商品更新
    public ServiceData update(ItemUpdateReqDto reqDto) {
        ServiceData serviceData=new ServiceData();
        Item item=new Item();
        item.setId(reqDto.getId());
        item.setBarcode(reqDto.getBarcode());
        item.setCid(reqDto.getCid());
        item.setImage(reqDto.getImage());
        item.setNum(reqDto.getNum());
        item.setPrice(reqDto.getPrice());
        item.setSellPoint(reqDto.getSellPoint());
        item.setTitle(reqDto.getTitle());
        //1.更新商品表
        boolean itemUpdate=itemMapper.update(item);
        boolean itemDescUpdate=true;
        boolean itemParamItemUpdate=true;
        if (reqDto.getDesc() != null && !reqDto.getDesc().isEmpty()){
            ItemDesc itemDesc=new ItemDesc();
            itemDesc.setItemId(reqDto.getId());
            itemDesc.setItemDesc(reqDto.getDesc());
            //2.更新商品描述
            itemDescUpdate=itemDescMapper.update(itemDesc);
        }
        if (reqDto.getItemParams() != null && !reqDto.getItemParams().isEmpty()){
            ItemParamItem itemParamItem=new ItemParamItem();
            itemParamItem.setId(reqDto.getItemParamId());
            itemParamItem.setItemId(reqDto.getId());
            itemParamItem.setParamData(reqDto.getItemParams());
            //3.更新商品規格內容
            itemParamItemUpdate=itemParamItemMapper.update(itemParamItem);
        }
        if (itemUpdate && itemDescUpdate && itemParamItemUpdate){
            serviceData.setStatus(200);
        }
        return serviceData;
    }

    //7.根据商品ID查询商品信息
    public ServiceData getItemById(Long itemId) throws Exception{
        ServiceData serviceData=new ServiceData();
        serviceData.setStatus(200);
        Item item=itemMapper.getItemById(itemId);
        if (item==null){
            serviceData.setMessage("商品信息不存在...");
            return serviceData;
        }
        serviceData.setMessage("查询商品信息成功...");
        serviceData.setData(item);
        return serviceData;
    }

}
