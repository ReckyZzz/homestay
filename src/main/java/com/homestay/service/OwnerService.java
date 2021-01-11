package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.vo.CommentVO;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO2;

import java.util.Date;
import java.util.List;

public interface OwnerService {
    int addImage(Image image);
    Room getRoomById(Integer id);
    List<RoomCollection> getCollections(User user);
    List<Comment> getCommentsByRoom(Room room);
    List<RoomVO2> getRooms(Integer ownerId);
    List<Room> searchRooms(String name);
    CommonResponse<Order> reserve(Integer userId, Integer roomId, Integer days, Date reserveDate);
    CommonResponse<Object> collectRoom(User user,Room room);
    CommonResponse<Object> cancelCollectRoom(User user,Room room);
    CommonResponse<Comment> commentRoom(Order order, Integer stars, String content);
    CommonResponse<List<CommentVO>> getComments(Integer ownerId);
    CommonResponse<PageInfo<OrderVO>> getOrders(Integer userId, Integer pageNum, Integer pageSize);
}
