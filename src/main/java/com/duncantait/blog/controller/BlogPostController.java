package com.duncantait.blog.controller;

import com.duncantait.blog.controller.model.BlogPostRequest;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.model.BlogTag;
import com.duncantait.blog.model.mapper.BlogPostMapper;
import com.duncantait.blog.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostMapper mapper;
    private final BlogPostService service;

    @PostMapping("/post")
    public BlogPost create(@RequestBody BlogPostRequest blogPostRequest) {
        BlogPost post = mapper.toDomain(blogPostRequest);
        return service.create(post);
    }


    @GetMapping("/post")
    public List<BlogPost> getAll(@RequestParam(value = "tags", required = false) String tags) {
        if (tags == null) {
            return service.getAll();
        }
        List<BlogTag> blogTags = mapper.tagsToDomain(tags);
        return service.getAllByTags(blogTags);
    }

}
