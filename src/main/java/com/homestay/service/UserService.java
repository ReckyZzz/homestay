package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;

public interface UserService {
    CommonResponse<User> login(User user);
    CommonResponse<User> register(User user);
    CommonResponse<User> resetPassword(String username,String newPwd);
}
