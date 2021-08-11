package com.duncantait.blog.repository.mapper;

import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.repository.model.PersistentBlogPost;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = BlogCommentPersistenceMapper.class)
public interface BlogPostPersistenceMapper {
    PersistentBlogPost toPersistentEntity(BlogPost blogPost);
    BlogPost toDomain(PersistentBlogPost persistentPost);
    List<BlogPost> toDomain(List<PersistentBlogPost> persistentPost);
}
