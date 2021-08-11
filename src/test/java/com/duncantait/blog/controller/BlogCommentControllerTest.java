package com.duncantait.blog.controller;

import com.duncantait.blog.BlogApplication;
import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.util.RequestFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = BlogApplication.class,
        properties = {})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogCommentControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.findAndRegisterModules();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private MockMvc mockMvc;

    private String postId;

    @Test
    @Order(1)
    void createPost() throws Exception {
        MvcResult blogPostResult = mockMvc.perform(postRequest("/post", RequestFactory.validCreateBlogPostRequestJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogPost blogPost = mapper.readValue(blogPostResult.getResponse().getContentAsString(), BlogPost.class);
        assertThat(blogPost).isNotNull();
        assertThat(blogPost.getId()).isEqualTo(1L);
        assertThat(blogPost.getBody()).contains("some text some text blah blah");
        assertThat(blogPost.getCreationDate()).isCloseToUtcNow(new TemporalUnitWithinOffset(1, ChronoUnit.HOURS));
        assertThat(blogPost.getTags()).hasSize(3);
        assertThat(blogPost.getTags()).allMatch(blogTag -> blogTag.getName().contains("tag"));

        postId = String.valueOf(blogPost.getId());
    }

    @Test
    @Order(2)
    void addCommentToPost() throws Exception {
        MvcResult commentResult = mockMvc.perform(postRequest(String.format("/post/%s/comment", postId), RequestFactory.validCreateCommentRequestJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogComment comment = mapper.readValue(commentResult.getResponse().getContentAsString(), BlogComment.class);
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(52L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getBody()).contains("a comment some text");
        assertThat(comment.getCreationDate()).isCloseToUtcNow(new TemporalUnitWithinOffset(1, ChronoUnit.HOURS));
    }

    @Test
    @Order(3)
    void getAllPosts() throws Exception {
        MvcResult allBlogPostsResult = mockMvc.perform(getRequest("/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<BlogPost> allBlogPosts = mapper.readValue(allBlogPostsResult.getResponse().getContentAsString(), new TypeReference<List<BlogPost>>() {});
        assertThat(allBlogPosts).isNotNull();
        assertThat(allBlogPosts).hasSize(1);
        BlogPost blogPost = allBlogPosts.get(0);
        assertThat(blogPost.getId()).isEqualTo(1L);
        assertThat(blogPost.getBody()).contains("some text some text blah blah");
        assertThat(blogPost.getTags()).hasSize(3);
        assertThat(blogPost.getTags()).allMatch(blogTag -> blogTag.getName().contains("tag"));
        assertThat(blogPost.getComments()).hasSize(1);
        BlogComment comment = blogPost.getComments().get(0);
        assertThat(comment.getId()).isEqualTo(52L);
        assertThat(comment.getPostId()).isEqualTo(1L);
        assertThat(comment.getBody()).contains("a comment some text");
    }

    protected MockHttpServletRequestBuilder postRequest(final String urlTemplate, final String jsonContent) {
        return post(URI.create(urlTemplate))
                .secure(true)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getRequest(final String urlTemplate) {
        return get(URI.create(urlTemplate))
                .secure(true)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

}