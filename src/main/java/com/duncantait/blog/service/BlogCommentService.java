package com.duncantait.blog.service;

import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.model.BlogTag;

import java.util.List;

public interface BlogCommentService {
    BlogComment addComment(BlogComment comment);
}
