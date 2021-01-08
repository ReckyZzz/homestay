package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;

import java.util.Date;
import java.util.List;

public interface UserService {
    boolean isOwner(int ownerId);
    boolean resetPassword(User user,String oldPassword,String newPassword);
    Integer getRoomNumByUser(Integer id);
    User getUserById(Integer id);
    Room getRoomById(Integer id);
    List<RoomCollection> getCollections(Integer userId);
    List<Comment> getCommentsByRoom(Integer roomId);
    List<Room> getRooms();
    List<Room> searchRooms(String name);
    CommonResponse<User> login(User user);
    CommonResponse<User> register(User user);
    CommonResponse<Order> reserve(Integer userId, Integer roomId, Integer days, Date reserveDate);
    CommonResponse<Object> checkRoomInfo(Integer roomId);
    CommonResponse<Object> collectRoom(User user,Integer roomId);
    CommonResponse<Object> cancelCollectRoom(User user,Room room);
    CommonResponse<Comment> commentRoom(Integer orderId,Integer stars,String content);
    CommonResponse<PageInfo<Order>> getOrders(Integer userId,Integer pageNum,Integer pageSize);
}
