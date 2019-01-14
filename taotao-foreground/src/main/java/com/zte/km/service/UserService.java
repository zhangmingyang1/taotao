package com.zte.km.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.cache.CacheManagerService;
import com.zte.km.common.consts.MQConst;
import com.zte.km.common.utils.CookieUtils;
import com.zte.km.common.utils.FtpUtil;
import com.zte.km.common.utils.HttpClientUtil;
import com.zte.km.dto.*;
import com.zte.km.dto.mq.MessageData;
import com.zte.km.dto.mq.MessageType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:FTP.properties")
public class UserService {

    @Autowired
    private HttpSession session;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CacheManagerService cacheManagerService;
    @Autowired
    private RabbitMQService rabbitMQService;
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

    @Value("${SSO_USER_URL}")
    private String SSO_USER_URL;

    @Value("${PROVINCE_CITY_COUNTY_URL}")
    private String PROVINCE_CITY_COUNTY_URL;

    //1.token查询用户信息
    public ServiceData token(String token) {
        String json = HttpClientUtil.doGet(SSO_USER_URL + token);
        ServiceData serviceData = JSON.parseObject(json, ServiceData.class);
        if (serviceData!=null && serviceData.getStatus()==200){
            //用户信息存入session
            session.setAttribute("user",serviceData.getData());
            return serviceData;
        }
        return null;
    }

    //2.用户安全退出
    public void logout(String token) {
        //删除session中用户信息
        session.removeAttribute("user");
        //删除redis缓存中的用户信息
        redisTemplate.delete("USER_MESSAGE:"+token);
        //清理cookie中的token
        CookieUtils.deleteCookie(request,response,"TOKEN");
    }

    //3.获取省市区信息
    public List<City> getCityMessage(Integer parentId) {
        return cacheManagerService.getAndSet("PROVINCE_CITY_COUNTY", new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                String response = HttpClientUtil.doGet(PROVINCE_CITY_COUNTY_URL+parentId);
                ServiceData serviceData = JSON.parseObject(response, ServiceData.class);
                if (serviceData != null && serviceData.getStatus() == 200) {
                    List<JSONObject> list = (List<JSONObject>) serviceData.getData();
                    return list.stream().map(x -> x.toJavaObject(City.class)).collect(Collectors.toList());
                }
                return new ArrayList<>();
            }
        },parentId);
    }

    //4.用户头像更新
    public ServiceData uploadImage(String ext, InputStream inputStream) {
        UserTotalMessage user =this.getUserMessage();
        //随机生成图片名
        String imageName = UUID.randomUUID().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd");
        String filePath = simpleDateFormat.format(new Date());
        //上传图片至FTP
        FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                FTP_BASEPATH, filePath, imageName + ext, inputStream);
        //图片路径
        String ImgPath="http://127.0.0.1:81" + FTP_BASEPATH + filePath + "/" + imageName + ext;
        //更新用户头像信息
        UserMessageImg userMessageImg=new UserMessageImg();
        userMessageImg.setUserId(user.getId());
        userMessageImg.setPicture(ImgPath);
        //发布消息
        rabbitMQService.SendUserMessage(new MessageData(MessageType.UserMessageImg,userMessageImg));
        return new ServiceData(200,ImgPath);
    }

    //5.用户基本信息更新
    public ServiceData userMessageUpdate(UserMessage userMessage) {
        UserTotalMessage user =this.getUserMessage();
        userMessage.setUserId(user.getId());
        //发布消息
        rabbitMQService.SendUserMessage(new MessageData(MessageType.UserMessage,userMessage));
        return new ServiceData(200,"用户信息更新成功!");
    }

    //6.用户更多信息更新
    public ServiceData userAddressUpdate(List<UserMessageAddress> userMessageAddressList) {
        UserTotalMessage user =this.getUserMessage();
        userMessageAddressList.forEach(x -> x.setUserId(user.getId()));
        //发布消息
        rabbitMQService.SendUserMessage(new MessageData(MessageType.UserMessageAddress,userMessageAddressList));
        return new ServiceData(200,"用户信息更新成功!");
    }

    private UserTotalMessage getUserMessage(){
        Object user = request.getAttribute("user");
        return (UserTotalMessage)user;
    }

}
