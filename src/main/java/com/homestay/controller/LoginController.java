package com.homestay.controller;

import com.homestay.entity.User;
import com.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    //将Service注入Web层
    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public String test(){
        return "index";
    }

    @RequestMapping("/log")
    public String test2(){
        return "login";
    }

    @RequestMapping(value = "/logIn",method = RequestMethod.POST)
    public String login(String username,String password){
        User user = userService.logIn(username,password);
        if(user!=null){
            return "index";
        }else {
            return "error";
        }
    }
}
