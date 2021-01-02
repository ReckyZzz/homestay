package com.homestay.service;

import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;

public interface UserService {
    CommonResponse<User> logIn(User user);
    CommonResponse<User> register(User user);
    CommonResponse<User> resetPassword(String username,String newPwd);
}
