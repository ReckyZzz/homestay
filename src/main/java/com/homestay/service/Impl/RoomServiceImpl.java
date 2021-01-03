package com.homestay.service.Impl;

import com.homestay.mapper.RoomMapper;
import com.homestay.pojo.Room;
import com.homestay.response.CommonResponse;
import com.homestay.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomMapper roomMapper;

    @Override
    public CommonResponse<Room> addRoom(Room room){
        if(room.getRoomOwner() != null && room.getRoomName() != null){
            Room databaseRoom = roomMapper.getRoomByRoomId(room.getRoomId());
        }
        else{
            return new CommonResponse<>(3,"房间信息不能为空！",null);
        }
    }
}
