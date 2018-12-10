package com.zte.km.dto;

/** 后台返回给前台的响应信息
 * Created by Administrator on 2018/11/3.
 */
public class ServiceData<T> {
    private Integer status;
    private String message;
    private T data;

    public ServiceData() {
    }

    public ServiceData(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
