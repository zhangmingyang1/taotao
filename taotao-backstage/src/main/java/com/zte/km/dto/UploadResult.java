package com.zte.km.dto;

/** 图片上传至FTP的结果
 * Created by Administrator on 2018/11/4.
 */
////成功时
//      {
//        "error" : 0,
//        "url" : "http://www.example.com/path/to/file.ext[上传的图片地址]"
//      }
////失败时
//      {
//         "error" : 1,
//         "message" : "错误信息"
//      }
public class UploadResult {
    private Integer error;
    private String url;
    private String message;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
