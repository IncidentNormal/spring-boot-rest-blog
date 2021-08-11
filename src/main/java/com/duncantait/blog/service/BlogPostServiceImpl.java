package com.duncantait.blog.service;

import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.model.BlogTag;
import com.duncantait.blog.repository.BlogPostRepository;
import com.duncantait.blog.repository.mapper.BlogPostPersistenceMapper;
import com.duncantait.blog.repository.model.PersistentBlogPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository repository;
    private final BlogPostPersistenceMapper mapper;

    @Override
    public BlogPost create(BlogPost post) {
        PersistentBlogPost entity = mapper.toPersistentEntity(post);
        PersistentBlogPost saved = repository.saveAndFlush(entity);
        log.info("created {}", saved);
        BlogPost blogPost = mapper.toDomain(saved);
        log.info("mapped {}", blogPost);
        return blogPost;
    }

    @Override
    public List<BlogPost> getAll() {
        List<PersistentBlogPost> entities = repository.findAll();
        log.info("fetched {}", entities);
        List<BlogPost> blogPosts = mapper.toDomain(entities);
        log.info("mapped {}", blogPosts);
        return blogPosts;
    }

    @Override
    public List<BlogPost> getAllByTags(List<BlogTag> tags) {
        Set<String> strTags = tags.stream().map(BlogTag::getName).collect(Collectors.toSet());
        List<PersistentBlogPost> entities = repository.findAllByTags(strTags);
        log.info("fetched {}", entities);
        List<BlogPost> blogPosts = mapper.toDomain(entities);
        log.info("mapped {}", blogPosts);
        return blogPosts;
    }

    @Override
    public List<BlogPost> getAllByFreeTextSearch(String terms) {
        return null;
    }
}
