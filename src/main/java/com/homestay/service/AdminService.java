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
    Room updateRoom(Room room);
    Room addRoom(Room room);
    Order updateOrder(Order order);
    Order addOrder(Order order);
    Comment updateComment(Comment comment);
    Comment addComment(Comment comment);
    void deleteUser(List<Integer> ids);
    void resetUser(List<Integer> ids);
    void deleteRoom(List<Integer> ids);
    void resetRoom(List<Integer> ids);
    void deleteOrder(List<Integer> ids);
    void resetOrder(List<Integer> ids);
    void deleteComment(List<Integer> ids);
    void resetComment(List<Integer> ids);
}
