package com.zte.km.service;

import com.zte.km.dao.ItemCatMapper;
import com.zte.km.dto.ItemCatNode;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.TreeNode;
import com.zte.km.entities.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/4.
 */
@Service
public class ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    //1.获取商品分类列表
    public List<TreeNode> getItemCatsByParentId(Long parentId) {
        List<ItemCat> itemCatList=itemCatMapper.getItemCatsByParentId(parentId);
        //分类列表转换成TreeNode的列表
        List<TreeNode> resultList = new ArrayList<>();
        for (ItemCat itemCat : itemCatList) {
            //创建一个TreeNode对象
            TreeNode node = new TreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            node.setState(itemCat.getParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    //2.根据id列表获取商品分类列表
    public List<ItemCat> getItemCatsByIds(List<Long> itemCatIdList) {
        if (itemCatIdList.isEmpty())
            return new ArrayList<>();
        return itemCatMapper.getItemCatsByIds(itemCatIdList);
    }

    //3.获取所有商品分类列表
    public ServiceData getItemCatList() {
        ServiceData<List<?>> serviceData=new ServiceData<>();
        List<?> itemCatList = this.getItemCatList(0L);
        //查询所有商品分类列表
        serviceData.setStatus(200);
        serviceData.setMessage("商品分类列表查询成功...");
        serviceData.setData(itemCatList);
        return serviceData;
    }

    //################私有方法#################//
    /**
     * 查询商品分类列表
     * @param parentId 父分类ID
     * @return 商品分类列表
     */
    private List<?> getItemCatList(long parentId) {
        //执行查询
        List<ItemCat> itemCatList=itemCatMapper.getItemCatsByParentId(parentId);
        //返回值列表
        List<Object> itemCatNodeList = new ArrayList<>();
        //向list中添加节点
        for (ItemCat itemCat : itemCatList) {
            //判断是否为父节点
            if (itemCat.getParent()) {
                ItemCatNode catNode = new ItemCatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItem(getItemCatList(itemCat.getId()));

                itemCatNodeList.add(catNode);
                //如果是叶子节点
            } else {
                itemCatNodeList.add("/products/"+itemCat.getId()+".html|" + itemCat.getName());
            }
        }
        return itemCatNodeList;
    }


}
