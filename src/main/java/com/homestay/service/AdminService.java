package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Comment;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;

import java.util.List;

public interface AdminService {
    CommonResponse<User> login(User admin);
    CommonResponse<PageInfo<User>> getUsers(Integer pageNum, Integer pageSize);
    CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize);
    CommonResponse<PageInfo<Order>> getOrders(Integer pageNum,Integer pageSize);
    CommonResponse<PageInfo<Comment>> getComments(Integer pageNum,Integer pageSize);
    User updateUser(User user);
    User addUser(User user);
    User getUserById(Integer id);
    Room updateRoom(Room room);
    Room addRoom(Room room);
    Order updateOrder(Order order);
    Order addOrder(Order order);
    Comment updateComment(Comment comment);
    Comment addComment(Comment comment);
    CommonResponse<Object> deleteUser(Integer userId);
    CommonResponse<Object> resetUser(Integer userId);
    CommonResponse<Object> deleteRoom(Integer roomId);
    CommonResponse<Object> resetRoom(Integer roomId);
    CommonResponse<Object> deleteOrder(Integer orderId);
    CommonResponse<Object> resetOrder(Integer orderId);
    CommonResponse<Object> deleteComment(Integer commentId);
    CommonResponse<Object> resetComment(Integer commentId);
}
