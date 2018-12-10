package com.zte.km.controller;

import com.zte.km.dto.SearchData;
import com.zte.km.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private SolrService solrService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String query(@RequestParam("key")String key,
                        @RequestParam(defaultValue="1")Integer page,
                        Model model){
        SearchData searchData=solrService.query(key, page);
        //向页面传递参数
        model.addAttribute("query", key);
        model.addAttribute("totalPages", searchData.getPageCount());
        model.addAttribute("itemList", searchData.getItemList());
        model.addAttribute("page", page);
        return "search";
    }



}
