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
    List<RoomVO2> getRooms(Integer ownerId);
    CommonResponse<Object> getIn(Integer orderId);
    CommonResponse<Object> disableRoom(Integer roomId);
    CommonResponse<Object> enableRoom(Integer roomId);
    CommonResponse<Room> addRoom(Integer ownerId,String roomName,String description, String location, Double price);
    CommonResponse<List<CommentVO>> getComments(Integer ownerId);
    CommonResponse<PageInfo<OrderVO>> getOrders(Integer userId, Integer pageNum, Integer pageSize);
}
