package com.duncantait.blog.controller;

import com.duncantait.blog.controller.model.BlogCommentRequest;
import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.mapper.BlogCommentMapper;
import com.duncantait.blog.service.BlogCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogCommentController {

    private final BlogCommentMapper mapper;
    private final BlogCommentService service;

    @PostMapping("/post/{id}/comment")
    public BlogComment create(@PathVariable(value="id") String postId, @RequestBody BlogCommentRequest blogCommentRequest) {
        BlogComment comment = mapper.toDomain(postId, blogCommentRequest);
        return service.addComment(comment);
    }

}
