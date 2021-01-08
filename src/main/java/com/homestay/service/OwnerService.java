package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;

import java.util.Date;
import java.util.List;

public interface OwnerService {
    int addImage(Image image);
    boolean resetPassword(User user,String oldPassword,String newPassword);
    Room getRoomById(Integer id);
    List<RoomCollection> getCollections(User user);
    List<Comment> getCommentsByRoom(Room room);
    List<Room> getRooms();
    List<Room> searchRooms(String name);
    CommonResponse<User> login(User user);
    CommonResponse<User> register(User user);
    CommonResponse<Order> reserve(Integer userId, Integer roomId, Integer days, Date reserveDate);
    CommonResponse<Object> collectRoom(User user,Room room);
    CommonResponse<Object> cancelCollectRoom(User user,Room room);
    CommonResponse<Comment> commentRoom(Order order, Integer stars, String content);
    CommonResponse<PageInfo<Order>> getOrders(Integer userId, Integer pageNum, Integer pageSize);
}
