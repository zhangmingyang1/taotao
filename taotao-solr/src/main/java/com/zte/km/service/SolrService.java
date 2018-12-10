package com.zte.km.service;

import com.zte.km.dao.SolrDao;
import com.zte.km.dto.ItemMessage;
import com.zte.km.dto.SearchData;
import com.zte.km.dto.ServiceData;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolrService {

    @Autowired
    private SolrDao solrDao;

    //1.查询商品信息
    public ServiceData inquire(String key, Integer page, Integer rows) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(key);
        //设置分页
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        //设置默认搜素域
        solrQuery.set("df", "item_keywords");
        //设置高亮显示
        solrQuery.setHighlight(true);    //打开高亮开关
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</span>");
        //执行查询
        SearchData searchData = solrDao.inquire(solrQuery);
        //计算查询结果总页数
        Long recordCount = searchData.getRecordCount();    //总记录数
        Long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchData.setPageCount(pageCount);    //总页数
        searchData.setCurPage(page);           //当前页
        ServiceData serviceData=new ServiceData();
        serviceData.setStatus(200);
        serviceData.setMessage("查询成功.");
        serviceData.setData(searchData);
        return serviceData;
    }

    //2.新增/更新商品信息
    public ServiceData addOrUpdate(ItemMessage itemMessage) {
        ServiceData serviceData=new ServiceData();
        try {
            solrDao.addOrUpdate(itemMessage);
            serviceData.setStatus(200);
            serviceData.setMessage("更新成功...");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            serviceData.setStatus(500);
            serviceData.setMessage(e.getMessage());
        }
        return serviceData;
    }

    //3.根据id删除索引
    public ServiceData delete(String id) {
        ServiceData serviceData=new ServiceData();
        try {
            solrDao.delete(id);
            serviceData.setStatus(200);
            serviceData.setMessage("删除成功...");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            serviceData.setStatus(500);
            serviceData.setMessage(e.getMessage());
        }
        return serviceData;
    }

    //4.删除所有索引库数据
    public ServiceData overall() {
        ServiceData serviceData=new ServiceData();
        try {
            solrDao.overall();
            serviceData.setStatus(200);
            serviceData.setMessage("删除成功...");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            serviceData.setStatus(500);
            serviceData.setMessage(e.getMessage());
        }
        return serviceData;
    }


}
