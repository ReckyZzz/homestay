package com.homestay.service.Impl;

import com.homestay.mapper.RoomMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean isValidRoom(Room room){
        User owner = userMapper.getUserById(room.getRoomOwner());
        if(owner.getUserType() == 1)
            return true;
        return false;
    }

    @Override
    public CommonResponse<Room> addRoom(Room room){
        if(room.getRoomOwner() != null && room.getRoomName() != null && room.getRoomPrice() != 0){
            Room databaseRoom = roomMapper.getRoomByName(room.getRoomName());
            if(databaseRoom == null && isValidRoom(room)){
                roomMapper.insertRoom(room);
                return new CommonResponse<>(0,"添加成功",room);
            }
            else if(databaseRoom != null){
                return new CommonResponse<>(1,"房间已存在",null);
            }
            else{
                return new CommonResponse<>(2,"房间所有者不合法",null);
            }
        }
        else{
            return new CommonResponse<>(3,"房间信息不能为空！",null);
        }
    }
}
