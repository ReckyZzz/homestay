package com.homestay.service;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Order;
import com.homestay.response.CommonResponse;

public interface OrderService {
    CommonResponse<PageInfo<Order>> getOrders(Integer pageNum,Integer pageSize);
}
