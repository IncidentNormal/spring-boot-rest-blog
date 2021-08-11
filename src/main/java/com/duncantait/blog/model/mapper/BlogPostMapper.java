package com.duncantait.blog.model.mapper;

import com.duncantait.blog.controller.model.BlogPostRequest;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.model.BlogTag;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface BlogPostMapper {
    BlogPost toDomain(BlogPostRequest request);

    default BlogTag map(String value) {
        return new BlogTag(value);
    }

    default Set<BlogTag> map(List<String> value) {
        if (value == null) {
            return null;
        }
        return value.stream()
                .map(BlogTag::new)
                .collect(Collectors.toSet());
    }

    default List<BlogTag> tagsToDomain(String tags) {
        if (tags == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.split(",")).map(BlogTag::new).collect(Collectors.toList());
    }
}
