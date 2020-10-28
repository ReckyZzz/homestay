package com.homestay.mapper;

import com.homestay.entity.Room;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomMapper {
    Room getRoomByRoomId(int roomId);
}
