package com.duncantait.blog.config;

import com.duncantait.blog.model.mapper.BlogCommentMapper;
import com.duncantait.blog.model.mapper.BlogPostMapper;
import com.duncantait.blog.repository.mapper.BlogCommentPersistenceMapper;
import com.duncantait.blog.repository.mapper.BlogPostPersistenceMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfig {

    @Bean
    public BlogCommentMapper blogCommentMapper() {
        return Mappers.getMapper(BlogCommentMapper.class);
    }
    @Bean
    public BlogPostMapper blogPostMapper() {
        return Mappers.getMapper(BlogPostMapper.class);
    }
    @Bean
    public BlogPostPersistenceMapper blogPostPersistenceMapper() {
        return Mappers.getMapper(BlogPostPersistenceMapper.class);
    }
    @Bean
    public BlogCommentPersistenceMapper blogCommentPersistenceMapper() {
        return Mappers.getMapper(BlogCommentPersistenceMapper.class);
    }
}
