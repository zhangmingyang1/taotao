package com.zte.km.service;

import com.zte.km.dao.UserMapper;
import com.zte.km.dto.ServiceData;
import com.zte.km.entities.User;
import com.zte.km.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RedisTemplate redisTemplate;

    //1.检查用户登陆信息
    public ServiceData check(String content, Integer type) {
        ServiceData serviceData=new ServiceData();
        User user=null;
        switch (type){
            case 1:
                user= userMapper.checkByName(content);break;
            case 2:
                user= userMapper.checkByPhone(content);break;
            case 3:
                user= userMapper.checkByEmail(content);break;
            default:break;
        }
        if (user == null){
            serviceData.setStatus(200);
            serviceData.setMessage("用户不存在，可以注册...");
            serviceData.setData(true);
        }else {
            serviceData.setStatus(200);
            serviceData.setMessage("用户已经存在，不可重复注册...");
            serviceData.setData(false);
        }
        return serviceData;
    }

    //2.用户注册
    public ServiceData register(User user) {
        String password = user.getPassword();
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5);
        boolean success = userMapper.register(user);
        if (success)
            return new ServiceData(200,"用户注册成功.");
        else
            return new ServiceData(500,"用户注册失败.");
    }

    //3.用户登陆
    public ServiceData login(String username, String password) {
        User user = userMapper.checkByName(username);
        if (user == null)
            return new ServiceData(400,"没有此用户信息...");
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5.equals(user.getPassword()))
            return new ServiceData(400,"用户密码错误...");
        String token = UUID.randomUUID().toString();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("USER_MESSAGE:"+token,user,1, TimeUnit.DAYS);
        ServiceData serviceData=new ServiceData();
        serviceData.setStatus(200);
        serviceData.setMessage("用户登录成功");
        serviceData.setData(token);
        //用户登陆成功之后，将token写入cookie，微服务之间实现共享
        //微服务之间相互调用，带着cookie中的token检查用户是否登陆
        CookieUtils.setCookie(request,response,"TOKEN",token);
        return serviceData;
    }

    //4.通过token检查用户信息
    public ServiceData token(String token) {
        ServiceData serviceData=new ServiceData();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        User user = (User) valueOperations.get("USER_MESSAGE:"+token);
        if (user == null)
            return new ServiceData(400,"用户信息已过期，请重新登陆...");
        redisTemplate.expire("USER_MESSAGE:"+token,30,TimeUnit.MINUTES);
        serviceData.setStatus(200);
        serviceData.setMessage("用户信息查询成功。");
        serviceData.setData(user);
        return serviceData;
    }

    //5.用户安全退出
    public void logout(String token) {
        //删除redis缓存中的用户信息
        redisTemplate.delete("USER_MESSAGE:"+token);
        //清理cookie中的token
        CookieUtils.deleteCookie(request,response,"TOKEN");
    }
}
