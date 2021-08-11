package com.duncantait.blog.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class BlogPostRequest {
    private String title;
    private String body;
    private List<String> tags;
}
