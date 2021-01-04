package com.homestay.service;

import com.homestay.pojo.Room;
import com.homestay.response.CommonResponse;

public interface RoomService {
    boolean isValidRoom(Room room);
    CommonResponse<Room> addRoom(Room room);
}
