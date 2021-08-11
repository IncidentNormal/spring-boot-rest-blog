package com.duncantait.blog.service;

import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.model.BlogTag;

import java.util.List;

public interface BlogPostService {
    BlogPost create(BlogPost post);
    List<BlogPost> getAll();
    List<BlogPost> getAllByTags(List<BlogTag> tags);
    List<BlogPost> getAllByFreeTextSearch(String terms);

}
