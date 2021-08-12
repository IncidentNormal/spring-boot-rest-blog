package com.duncantait.blog.controller;

import com.duncantait.blog.BlogApplication;
import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.util.RequestFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
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
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AbstractControllerIT {

    protected static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.findAndRegisterModules();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    private MockMvc mockMvc;

    protected BlogPost createPost(String... tags) throws Exception {
        String body = RequestFactory.validCreateBlogPostRequestJson(tags);
        MvcResult blogPostResult = mockMvc.perform(postRequest("/post", body))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogPost blogPost = mapper.readValue(blogPostResult.getResponse().getContentAsString(), BlogPost.class);
        assertThat(blogPost).isNotNull();
        return blogPost;
    }

    protected BlogComment addCommentToPost(String postId) throws Exception {
        MvcResult commentResult = mockMvc.perform(postRequest(String.format("/post/%s/comment", postId), RequestFactory.validCreateCommentRequestJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogComment comment = mapper.readValue(commentResult.getResponse().getContentAsString(), BlogComment.class);
        assertThat(comment).isNotNull();
        return comment;
    }

    protected List<BlogPost> getAllPosts(String... tags) throws Exception {
        MockHttpServletRequestBuilder request = getRequest("/post");
        if (tags.length > 0) {
            String queryParamTags = String.join(",", tags);
            request.queryParam("tags", queryParamTags);
        }
        MvcResult allBlogPostsResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<BlogPost> allBlogPosts = mapper.readValue(allBlogPostsResult.getResponse().getContentAsString(), new TypeReference<List<BlogPost>>() {});
        assertThat(allBlogPosts).isNotNull();
        return allBlogPosts;
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