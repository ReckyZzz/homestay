package com.homestay.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.homestay.pojo.Comment;

@Mapper
public interface CommentMapper {
    Comment getCommentByUserId(int userId);
    int deleteCommentByUserId(int userId);
    int resetCommentByUserId(int userId);
}
