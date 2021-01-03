package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;

import java.util.List;

public interface AdminService {
    CommonResponse<User> login(User admin);
    CommonResponse<PageInfo<User>> getUsers(Integer pageNum, Integer pageSize);
    CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize);
    User updateUser(User user);
    Room updateRoom(Room room);
    User addUser(User user);
    Room addRoom(Room room);
    void deleteUser(List<Integer> ids);
    void resetUser(List<Integer> ids);
}
