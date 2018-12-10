package com.zte.km.service;

import com.sun.org.apache.regexp.internal.RE;
import com.zte.km.dao.ContentCategoryMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.TreeNode;
import com.zte.km.entities.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    //1.获取内容分类列表
    public List<TreeNode> getContentCatList(Long parentId) {
        List<ContentCategory> contentCategoryList=contentCategoryMapper.getContentCatList(parentId);
        List<TreeNode> treeNodeList=new ArrayList<>();
        for (ContentCategory contentCategory: contentCategoryList) {
            TreeNode treeNode=new TreeNode();
            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getParent()?"closed":"open");
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    //2.新增内容分类
    public ServiceData createContentCat(Long parentId, String name) {
        ServiceData serviceData=new ServiceData();
        ContentCategory contentCategory=new ContentCategory();
        contentCategory.setName(name);
        contentCategory.setParent(false);
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategoryMapper.createContentCat(contentCategory);
        //查看父节点的isParent列是否为true，如果不是true改成true
        ContentCategory parentCat = contentCategoryMapper.getContentCatById(parentId);
        //判断是否为true
        if(!parentCat.getParent()) {
            parentCat.setParent(true);
            //更新父节点
            contentCategoryMapper.updateContentCatById(parentCat);
        }
        serviceData.setStatus(200);
        serviceData.setData(contentCategory);
        return serviceData;
    }

    //3.更新内容分类
    public ServiceData updateContentCat(Long id, String name) {
        ServiceData serviceData=new ServiceData();
        ContentCategory contentCategory=new ContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateContentCatById(contentCategory);
        serviceData.setStatus(200);
        serviceData.setMessage("更新内容分类成功...");
        return serviceData;
    }

    //4.删除内容分类
    public ServiceData deleteContentCat(Long id) {
        ServiceData serviceData=new ServiceData();
        serviceData.setStatus(200);
        List<ContentCategory> childNode=contentCategoryMapper.getContentCatList(id);
        if (childNode==null||childNode.isEmpty()){
            ContentCategory contentCat = contentCategoryMapper.getContentCatById(id);
            Long parentId = contentCat.getParentId();
            contentCategoryMapper.deleteContentCatById(id);
            //查看parentId节点是否还有子节点
            List<ContentCategory> contentCategoryList=contentCategoryMapper.getContentCatList(parentId);
            if(contentCategoryList==null||contentCategoryList.isEmpty()){
                ContentCategory contentCategory = new ContentCategory();
                contentCategory.setId(parentId);
                contentCategory.setParent(false);
                contentCategoryMapper.updateContentCatById(contentCategory);
            }
        }
        return serviceData;
    }
}
