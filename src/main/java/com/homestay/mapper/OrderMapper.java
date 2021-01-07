package com.homestay.mapper;

import com.homestay.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order getOrderByOrderId(int orderId);
    List<Order> getOrderByUser(int uerId);
    List<Order> getOrderByOwner(int ownerId);
    List<Order> getOrderByRoom(int roomId);
    List<Order> list();
    List<Order> getAllOrders();
    int insertOrder(Order order);
    int updateOrder(Order order);
    int deleteOrderById(int id);
    int resetOrderById(int id);
}
