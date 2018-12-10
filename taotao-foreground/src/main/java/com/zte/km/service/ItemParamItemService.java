package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.ItemDesc;
import com.zte.km.dto.ItemParamItem;
import com.zte.km.dto.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
public class ItemParamItemService {

    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

    @Autowired
    private CacheManagerService cacheManagerService;

    //1.根据商品ID查询商品规格参数
    public String getItemParamItemById(Long itemId) {
        return cacheManagerService.getAndSet("ITEM_PARAM", new Callable<String>() {
            @Override
            public String call() throws Exception {
                //调用微服务，查询商品描述
                String response = HttpClientUtil.doGet(ITEM_PARAM_URL + itemId);
                if (response==null || response.isEmpty())
                    return null;
                ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                if (serviceData != null && serviceData.getStatus() == 200 && serviceData.getData()!=null) {
                    JSONObject jsonObject = (JSONObject) serviceData.getData();
                    ItemParamItem itemParamItem = jsonObject.toJavaObject(ItemParamItem.class);
                    String paramData = itemParamItem.getParamData();
                    List<Map> jsonList  = JSONObject.parseArray(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for(Map m1:jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for(Map m2:list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                            sb.append("            <td>"+m2.get("v")+"</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
                return null;
            }
        },itemId);
    }


}
