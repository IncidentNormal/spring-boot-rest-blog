package com.duncantait.blog.repository.mapper;

import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.repository.model.PersistentComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BlogCommentPersistenceMapper {
    PersistentComment toPersistentEntity(BlogComment blogComment);

    @Mapping(source = "post.id", target = "postId")
    BlogComment toDomain(PersistentComment persistentComment);
}
