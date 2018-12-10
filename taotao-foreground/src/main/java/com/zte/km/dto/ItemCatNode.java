package com.zte.km.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** 1、分类列表的节点。包含u、n、i属性。
 * Created by Administrator on 2018/11/7.
 */
public class ItemCatNode {
    @JSONField(name = "n")
    private String name;
    @JSONField(name = "u")
    private String url;
    @JSONField(name = "i")
    private List<?> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }
}
