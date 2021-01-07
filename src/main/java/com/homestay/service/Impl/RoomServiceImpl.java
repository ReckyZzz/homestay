package com.homestay.service.Impl;

import com.homestay.mapper.RoomMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.RoomService;
import com.homestay.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;

}
