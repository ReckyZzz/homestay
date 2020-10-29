package com.homestay.service;

import com.homestay.entity.User;

public interface UserService {
    User logIn(String name, String password);
    int register(User user);
    int resetPassword(String username,String newPwd);
}
