package com.homestay.controller;

import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    //用户是否登录
    @RequestMapping("/isLogin")
    public CommonResponse<Object> isLogin(HttpSession session){
        if(session.getAttribute(("user")) != null){
            return new CommonResponse<>(0,"已登录",null);
        }
        else{
            return new CommonResponse<>(1,"未登录",null);
        }
    }

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResponse<User> login(String username,String password,HttpSession session){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        SessionUtil.setSession(session);
        return userService.login(user);
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResponse<User> register(String username,String password){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        return userService.register(user);
    }

    //重置密码
    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    public CommonResponse<User> resetPassword(String username,String newPwd){
        return userService.resetPassword(username,newPwd);
    }
}
