package com.homestay.mapper;

import com.homestay.entity.User;

public interface UserMapper {
    User getUserById(Integer id);
    User getUserByInfo(String username,String password);
    Integer insertUser(User user);
}
