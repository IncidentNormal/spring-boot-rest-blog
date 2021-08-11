package com.duncantait.blog.repository.mapper;

import com.duncantait.blog.controller.model.BlogCommentRequest;
import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.repository.model.PersistentComment;
import org.mapstruct.Mapper;

@Mapper
public interface BlogCommentPersistenceMapper {
    PersistentComment toPersistentEntity(BlogComment blogComment);
    BlogComment toDomain(PersistentComment persistentComment);
}
