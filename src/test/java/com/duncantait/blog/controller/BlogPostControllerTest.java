package com.duncantait.blog.controller;

import com.duncantait.blog.model.BlogPost;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Primarily setup to test search by tag
 * Works the 2 endpoints:
 * - Create post (POST /post)
 * - Get all posts by tag (GET /post?tags=a,b,c)
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BlogPostControllerTest extends AbstractControllerIT {

    private List<Long> postIds;

    @BeforeAll
    void setup() {
        postIds = new ArrayList<>();
    }

    @Test
    @Order(1)
    void testCreatePosts() throws Exception {
        int postsToCreate = 3;
        for (int i = 0; i < postsToCreate; i++) {
            BlogPost post = createPost("tagA", String.format("tag%s", i));
            postIds.add(post.getId());
        }

        assertThat(postIds).containsExactlyInAnyOrder(1L, 2L, 3L);
    }

    @Test
    @Order(2)
    void testGetCommonTag() throws Exception {
        List<BlogPost> blogPosts = getAllPosts("tagA");

        assertThat(blogPosts).hasSize(3);
        List<Long> postIds = blogPosts.stream()
                .map(BlogPost::getId)
                .collect(Collectors.toList());
        assertThat(this.postIds).containsExactlyInAnyOrderElementsOf(postIds);
    }

    @Test
    @Order(3)
    void testGetSingleTag() throws Exception {
        List<BlogPost> blogPosts = getAllPosts("tag1");

        assertThat(blogPosts).hasSize(1);
    }

    @Test
    @Order(4)
    void testGetMultipleTags() throws Exception {
        List<BlogPost> blogPosts = getAllPosts("tag1", "tag2");

        assertThat(blogPosts).hasSize(2);
    }

}