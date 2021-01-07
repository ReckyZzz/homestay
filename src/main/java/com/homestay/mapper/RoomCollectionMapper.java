package com.homestay.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.homestay.pojo.RoomCollection;

import java.util.List;

@Mapper
public interface RoomCollectionMapper {
    RoomCollection getCollectionById(int id);
    List<RoomCollection> getCollectionByUser(int userId);
    List<RoomCollection> getAllCollectionByUser(int userId);
    int insertCollection(int userId,int roomId);
    int deleteCollectionByUserRoom(int userId,int roomId);
    int resetCollectionByUserRoom(int userId,int roomId);
    int deleteCollectionById(int id);
    int resetCollectionById(int id);
}
