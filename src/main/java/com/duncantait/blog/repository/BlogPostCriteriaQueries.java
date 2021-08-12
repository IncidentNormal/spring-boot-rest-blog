package com.duncantait.blog.repository;

import com.duncantait.blog.repository.model.PersistentBlogPost;

import java.util.Collection;
import java.util.List;

public interface BlogPostCriteriaQueries  {
    List<PersistentBlogPost> findByTagsWithCriteriaQuery(Collection<String> tags);
}
