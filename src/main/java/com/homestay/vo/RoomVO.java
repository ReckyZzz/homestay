package com.homestay.vo;

import com.homestay.pojo.Comment;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;

import java.util.Date;
import java.util.List;

public class RoomVO {
    private String roomName;
    private String ownerName;
    private String ownerDescription;
    private String url;
    private String location;
    private Integer roomNum;
    private Double price;
    private List<String> userNames;
    private List<String> content;
    private List<Integer> stars;
    private List<String> dates;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerDescription() {
        return ownerDescription;
    }

    public void setOwnerDescription(String ownerDescription) {
        this.ownerDescription = ownerDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<Integer> getStars() {
        return stars;
    }

    public void setStars(List<Integer> stars) {
        this.stars = stars;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
