package com.homestay.controller;

import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/isLogin")
    public CommonResponse<Object> isLogin(HttpSession session){
        if(session.getAttribute(("user")) != null){
            return new CommonResponse<>(0,"已登录",null);
        }
        else{
            return new CommonResponse<>(1,"未登录",null);
        }
    }

    @RequestMapping(value = "/logIn",method = RequestMethod.POST)
    public String login(String username,String password){
        /*User user = userService.logIn(username,password);
        if(user!=null){
            return "index";
        }else {
            return "error";
        }*/
        return "index";
    }
}
