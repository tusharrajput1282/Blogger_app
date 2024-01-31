package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentById(long postId);

    List<CommentDto> getAllComments();

}
