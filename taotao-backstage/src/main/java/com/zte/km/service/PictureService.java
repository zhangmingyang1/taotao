package com.zte.km.service;

import com.zte.km.dto.UploadResult;
import com.zte.km.common.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018/11/4.
 */
@Service
@PropertySource("classpath:FTP.properties")
public class PictureService {
    //通过properties文件自动注入
    @Value("${FTP_HOST}")
    private String FTP_HOST;    //ftp服务器ip
    @Value("${FTP_PORT}")
    private int FTP_PORT;        //ftp服务器端口
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;//用户名
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;//密码
    @Value("${FTP_BASEPATH}")
    private String FTP_BASEPATH;//存放文件的基本路径

    //1.图片上传至FTP
    public UploadResult uploadPicture(MultipartFile multipartFile) {
        UploadResult response = new UploadResult();
        //取文件扩展名
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //随机生成图片名
        String imageName = UUID.randomUUID().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd");
        String filePath = simpleDateFormat.format(new Date());
        try {
            //上传图片至FTP
            FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    FTP_BASEPATH, filePath, imageName + ext, multipartFile.getInputStream());
            response.setError(0);
            response.setUrl("http://127.0.0.1:81" + FTP_BASEPATH + filePath + "/" + imageName + ext);
        } catch (IOException e) {
            e.printStackTrace();
            response.setError(1);
            response.setMessage("上传图片失败:" + e);
        }
        return response;
    }
}
