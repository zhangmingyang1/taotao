package com.zte.km.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zte.km.common.utils.CookieUtils;
import com.zte.km.dto.ServiceData;
import com.zte.km.dto.User;
import com.zte.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        //1.取出token
        String token = CookieUtils.getCookieValue(request, "TOKEN");
        if(token==null || token.isEmpty()){
            //跳转到登陆页面，把用户请求的URL作为参数传递给页面
            response.sendRedirect(SSO_BASE_URL+"user/login?redirect="+requestURL.toString());
            return false;
        }
        //2.由token去redis获取用户信息
        ServiceData serviceData=userService.token(token);
        if (serviceData != null && serviceData.getStatus()==200){
            JSONObject jsonObject=(JSONObject) serviceData.getData();
            User user = jsonObject.toJavaObject(User.class);
            if (user == null){    //用户信息已经过期
                //跳转到登陆页面，把用户请求的URL作为参数传递给页面
                response.sendRedirect(SSO_BASE_URL+"user/login?redirect="+requestURL.toString());
                return false;
            }
            //将用户信息放入request域，controller中可取出user信息，补全订单中用户信息
            request.setAttribute("user",user);
            return true;    //放行
        }else {
            //跳转到登陆页面，把用户请求的URL作为参数传递给页面
            response.sendRedirect(SSO_BASE_URL+"user/login?redirect="+requestURL.toString());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
