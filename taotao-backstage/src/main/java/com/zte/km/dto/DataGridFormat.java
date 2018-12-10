package com.zte.km.dto;

import java.util.List;

/**
 * Easyui中datagrid控件要求的数据格式为：
 *   {total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}
 * Created by Administrator on 2018/11/3.
 */

public class DataGridFormat {
    private Long total;
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
