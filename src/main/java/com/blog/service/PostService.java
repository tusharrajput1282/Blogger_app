package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public  void deletePost(long id);

    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto updatePost(long postId, PostDto postDto);
}
