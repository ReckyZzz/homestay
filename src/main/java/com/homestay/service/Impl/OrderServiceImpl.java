package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.OrderMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.Order;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public CommonResponse<PageInfo<Order>> getOrders(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.list();
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public boolean isValidOrder(Order order){
        User user = userMapper.getUserById(order.getUserId());
        if(user.getUserType() == 1)
            return true;
        return false;
    }
}
