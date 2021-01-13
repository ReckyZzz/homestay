package com.homestay.mapper;

import com.homestay.pojo.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    Room getRoomByRoomId(int roomId);
    Room getARoomByName(String name);
    List<Room> getRoomByName(String name);
    List<Room> getRoomByOwner(int ownerId);
    List<Room> list();
    List<Room> getAllRooms();
    int enableRoom(Integer roomId);
    int disableRoom(Integer roomId);
    int insertRoom(Room room);
    int updateRoom(Room room);
    int deleteRoomById(int id);
    int resetRoomById(int id);
}
