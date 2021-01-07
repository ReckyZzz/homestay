package com.homestay.mapper;

import com.homestay.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    User getUserById(int id);
    int insertUser(User user);
    int resetPasswordByUsername(String username,String newPwd);
    int deleteUserByUsername(String username);
    int deleteUserById(int id);
    int resetUserByUsername(String username);
    int resetUserById(int id);
    int updateUser(User user);
    String getPasswordByUsername(String username);
    List<User> list();
    List<User> getAllUsers();
    List<User> getUsersByUsername(String username);
}
