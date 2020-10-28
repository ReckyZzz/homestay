package com.homestay.service.Impl;

import com.homestay.entity.User;
import com.homestay.mapper.UserMapper;
import com.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(){
    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public User logIn(String name, String password) {
        return userMapper.getUserByInfo(name,password);
    }

    @Override
    public Integer register(User user){
        if(user.getUserName()!=null && user.getUserPwd()!=null)
            return userMapper.insertUser(user);
        else
            return 0;
    }
}
