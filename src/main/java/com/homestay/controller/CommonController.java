package com.homestay.controller;

import com.homestay.entity.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//@RestController("/user")
public class CommonController {

    @Resource
    private UserService userService;

    //@PostMapping("/login")
    public CommonResponse<User> login(User user) {
        User u = userService.logIn(user.getUserName(), user.getUserPwd());
        if (u != null) {
            return new CommonResponse<>(200, "登录成功", u);
        } else {
            return new CommonResponse<>(400, "登录失败", null);
        }
    }
}
