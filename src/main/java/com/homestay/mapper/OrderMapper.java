package com.homestay.mapper;

import com.homestay.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Order getOrderByOrderId(int orderId);
}
