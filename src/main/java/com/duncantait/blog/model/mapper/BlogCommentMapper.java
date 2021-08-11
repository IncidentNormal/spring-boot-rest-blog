package com.duncantait.blog.model.mapper;

import com.duncantait.blog.controller.model.BlogCommentRequest;
import com.duncantait.blog.model.BlogComment;
import org.mapstruct.Mapper;

@Mapper
public interface BlogCommentMapper {
    BlogComment toDomain(String postId, BlogCommentRequest request);
}
