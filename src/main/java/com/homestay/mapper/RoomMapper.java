package com.homestay.mapper;

import com.homestay.pojo.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    Room getRoomByRoomId(int roomId);
    List<Room> getRoomByOwner(int ownerId);
    int insertRoom(Room room);
    int updateRoom(Room room);
    List<Room> list();
}
