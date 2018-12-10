package com.zte.km.dao;

import com.zte.km.dto.ItemMessage;
import com.zte.km.dto.SearchData;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SolrDao {

    @Autowired
    private SolrClient solrClient;

    //1.查询商品信息
    public SearchData inquire(SolrQuery solrQuery) throws IOException, SolrServerException {
        SearchData searchData=new SearchData();
        QueryResponse queryResponse = solrClient.query(solrQuery);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //取查询结果总数量
        searchData.setRecordCount(solrDocumentList.getNumFound());
        //商品列表
        List<ItemMessage> itemList = new ArrayList<>();
        //取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //取商品列表
        for (SolrDocument solrDocument : solrDocumentList) {
            //创建一商品对象
            ItemMessage item = new ItemMessage();
            item.setId((String) solrDocument.get("id"));
            //取高亮显示的结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = null;
            if (list != null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            //添加的商品列表
            itemList.add(item);
        }
        searchData.setItemList(itemList);
        return searchData;
    }

    //2.新增/更新商品信息
    public void addOrUpdate(ItemMessage itemMessage) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", itemMessage.getId());
        document.setField("item_title", itemMessage.getTitle());
        document.setField("item_price", itemMessage.getPrice());
        document.setField("item_sell_point", itemMessage.getSell_point());
        document.setField("item_image", itemMessage.getImage());
        document.setField("item_category_name", itemMessage.getCategory_name());
        document.setField("item_desc", itemMessage.getItem_des());
        solrClient.add(document);
        solrClient.commit();
    }

    //3.根据id删除索引
    public void delete(String id) throws IOException, SolrServerException {
        solrClient.deleteById(id);
        solrClient.commit();
    }

    //4.删除所有索引库数据
    public void overall() throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }


}
