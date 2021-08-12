package com.duncantait.blog.controller;

import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.BlogPost;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Primarily setup to adding a comment to an existing post works
 * Works the 3 endpoints:
 * - Create post (POST /post)
 * - Add comment to post (POST /post/{id}/comment)
 * - Get all posts (GET /post)
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BlogCommentControllerTest extends AbstractControllerIT {

    private Long postId;
    private Long commentId;

    @Test
    @Order(1)
    void testCreatePost() throws Exception {
        BlogPost blogPost = createPost();

        assertThat(blogPost).isNotNull();
        assertThat(blogPost.getId()).isEqualTo(1L);
        assertThat(blogPost.getBody()).contains("some text some text blah blah");
        assertThat(blogPost.getCreationDate()).isCloseToUtcNow(new TemporalUnitWithinOffset(1, ChronoUnit.HOURS));
        assertThat(blogPost.getTags()).hasSize(3);
        assertThat(blogPost.getTags()).allMatch(blogTag -> blogTag.getName().contains("tag"));

        postId = blogPost.getId();
    }

    @Test
    @Order(2)
    void testAddCommentToPost() throws Exception {
        BlogComment comment = addCommentToPost(String.valueOf(postId));

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(52L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getBody()).contains("a comment some text");
        assertThat(comment.getCreationDate()).isCloseToUtcNow(new TemporalUnitWithinOffset(1, ChronoUnit.HOURS));

        commentId = comment.getId();
    }

    @Test
    @Order(3)
    void testGetAllPosts() throws Exception {
        List<BlogPost> allBlogPosts = getAllPosts();

        assertThat(allBlogPosts).isNotNull();
        assertThat(allBlogPosts).hasSize(1);
        BlogPost blogPost = allBlogPosts.get(0);
        assertThat(blogPost.getId()).isEqualTo(1L).isEqualTo(postId);
        assertThat(blogPost.getBody()).contains("some text some text blah blah");
        assertThat(blogPost.getTags()).hasSize(3);
        assertThat(blogPost.getTags()).allMatch(blogTag -> blogTag.getName().contains("tag"));
        assertThat(blogPost.getComments()).hasSize(1);
        BlogComment comment = blogPost.getComments().get(0);
        assertThat(comment.getId()).isEqualTo(52L).isEqualTo(commentId);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getBody()).contains("a comment some text");
    }

}