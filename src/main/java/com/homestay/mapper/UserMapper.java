package com.homestay.mapper;

import com.homestay.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    User getUserByInfo(String username,String password);
    int insertUser(User user);
    int getUserIdByUsername(String username);
    int resetPasswordById(int userId,String newPwd);
    int resetPasswordByUsername(String username,String newPwd);
    int deleteUserByUsername(String username);
    int resetUserByUsername(String username);
    String getPasswordByUsername(String username);
}
