package com.homestay.mapper;

import com.homestay.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Order getOrderByOrderId(int orderId);
    int deleteOrderByUserId(int userId);
    int resetOrderByUserId(int userId);
}
