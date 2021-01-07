package com.homestay.pojo;

public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer roomId;
    private Integer rateStars;
    private String content;
    private Integer delFlag;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRateStars() {
        return rateStars;
    }

    public void setRateStars(Integer rateStars) {
        this.rateStars = rateStars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
