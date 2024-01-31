package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto dto= commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment is Deleted!!",HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable long postId){
        List<CommentDto> dto= commentService.getCommentById(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> dto = commentService.getAllComments();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
