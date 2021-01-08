package com.homestay.mapper;

import com.homestay.pojo.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    List<Image> getImagesByRoom(int roomId);
    int insertImage(Image image);
    int updateImage(Image image);
}
