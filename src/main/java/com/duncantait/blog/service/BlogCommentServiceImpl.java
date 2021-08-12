package com.duncantait.blog.service;

import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.repository.BlogCommentRepository;
import com.duncantait.blog.repository.BlogPostRepository;
import com.duncantait.blog.repository.mapper.BlogCommentPersistenceMapper;
import com.duncantait.blog.repository.model.PersistentBlogPost;
import com.duncantait.blog.repository.model.PersistentComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {

    private final BlogPostRepository postRepository;
    private final BlogCommentRepository commentRepository;
    private final BlogCommentPersistenceMapper mapper;

    @Override
    @Transactional
    public BlogComment addComment(BlogComment comment) {
        PersistentComment persistentComment = mapper.toPersistentEntity(comment);
        Long postId = comment.getPostId();
        PersistentComment saved = update(persistentComment, postId);
        return mapper.toDomain(saved);
    }

    private PersistentComment update(PersistentComment persistentComment, Long postId) {
        Optional<PersistentBlogPost> blogPost = postRepository.findById(postId);
        if (blogPost.isEmpty()) {
            throw new IllegalStateException(String.format("post with id=%s does not exist", postId));
        }
        blogPost.get().addComment(persistentComment);
        postRepository.save(blogPost.get());
        PersistentComment saved = commentRepository.saveAndFlush(persistentComment);
        return saved;
    }
}
