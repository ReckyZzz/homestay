package com.homestay.vo;

import com.homestay.pojo.Comment;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;

import java.util.List;

public class RoomVO {
    Room room;
    User roomOwner;
    List<Comment> comments;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(User roomOwner) {
        this.roomOwner = roomOwner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
