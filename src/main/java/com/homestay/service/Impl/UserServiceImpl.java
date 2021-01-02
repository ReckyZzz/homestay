package com.homestay.service.Impl;

import com.homestay.pojo.User;
import com.homestay.mapper.UserMapper;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(){
    }

    @Resource
    private UserMapper userMapper;

    @Override
    public CommonResponse<User> logIn(User user) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        User userInfo = userMapper.getUserByUsername(user.getUserName());
        if(userInfo != null){
            if(EncryptUtil.check(user.getUserPwd(),userInfo.getUserPwd())) {
                if (session != null) {
                    session.setAttribute("user", user);
                }
                return new CommonResponse<>(0, "登录成功", userInfo);
            }
            else{
                return new CommonResponse<>(1,"密码错误",null);
            }
        }
        else{
            return new CommonResponse<>(2,"用户名错误",null);
        }
    }

    @Override
    public CommonResponse<User> register(User user){
        if(user.getUserName()!=null && user.getUserPwd()!=null) {
            User databaseUser = userMapper.getUserByUsername(user.getUserName());
            if(databaseUser == null){
                user.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
                userMapper.insertUser(user);
                return new CommonResponse<>(0,"注册成功",user);
            }
            else{
                return new CommonResponse<>(1,"用户已存在",null);
            }
        }
        else {
            return new CommonResponse<>(2,"用户名或密码为空",null);
        }
    }

    @Override
    public CommonResponse<User> resetPassword(String username,String newPwd){
        userMapper.resetPasswordByUsername(username,EncryptUtil.getEncodedString(newPwd));
        User user = userMapper.getUserByUsername(username);
        return new CommonResponse<>(0,"重置密码成功",user);
    }
}
