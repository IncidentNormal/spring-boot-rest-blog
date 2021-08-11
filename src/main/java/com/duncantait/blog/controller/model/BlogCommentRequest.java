package com.duncantait.blog.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class BlogCommentRequest {
    private String title;
    private String body;
}
