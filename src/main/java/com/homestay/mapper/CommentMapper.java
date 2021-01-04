package com.homestay.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.homestay.pojo.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    Comment getCommentById(int commentId);
    List<Comment> getCommentByUser(int userId);
    List<Comment> getCommentByRoom(int roomId);
    List<Comment> list();
    int deleteCommentById(int id);
    int resetCommentById(int id);
    int insertComment(Comment comment);
    int updateComment(Comment comment);
}
