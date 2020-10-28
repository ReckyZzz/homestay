package com.homestay.mapper;

import com.homestay.entity.Order;

public interface OrderMapper {
    Order getOrderByOrderId(int orderId);
}
