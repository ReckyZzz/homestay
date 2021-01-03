package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;

import java.util.List;

public interface AdminService {
    CommonResponse<User> login(User admin);
    CommonResponse<PageInfo<User>> getUsers(Integer pageNum, Integer pageSize);
    User updateUser(User user);
    User addUser(User user);
    void deleteUser(List<Integer> ids);
    void resetUser(List<Integer> ids);
}
