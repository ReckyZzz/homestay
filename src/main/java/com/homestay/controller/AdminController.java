package com.homestay.controller;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.UserService;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    //管理员是否登录
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
    public CommonResponse<User> login(String username, String password, HttpSession session){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        SessionUtil.setSession(session);
        return adminService.login(user);
    }

    //获取用户列表
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public CommonResponse<PageInfo<User>> getStudents(Integer pageNum, Integer pageSize){
        return adminService.getUsers(pageNum,pageSize);
    }

    //更新用户信息
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public CommonResponse<User> updateUser(User user){
        user = adminService.updateUser(user);
        if(user != null){
            return new CommonResponse<>(0,"修改成功",user);
        }
        return new CommonResponse<>(1,"不存在的用户",null);
    }

    //添加用户
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public CommonResponse<User> addUser(User user){
        user = adminService.addUser(user);
        if(user != null){
            return new CommonResponse<>(0,"添加成功",user);
        }
        return new CommonResponse<>(1,"用户已存在",null);
    }
}
