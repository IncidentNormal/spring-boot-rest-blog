package com.duncantait.blog.controller;

import com.duncantait.blog.BlogApplication;
import com.duncantait.blog.model.BlogComment;
import com.duncantait.blog.model.BlogPost;
import com.duncantait.blog.util.RequestFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = BlogApplication.class,
        properties = {})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    void addCommentToPost() throws Exception {

        MvcResult blogPostResult = mockMvc.perform(postRequest("/post", RequestFactory.validCreateBlogPostRequestJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogPost blogPost = mapper.readValue(blogPostResult.getResponse().getContentAsString(), BlogPost.class);
        assertThat(blogPost).isNotNull();

        String postId = String.valueOf(blogPost.getId());

        MvcResult commentResult = mockMvc.perform(postRequest(String.format("/post/%s/comment", postId), RequestFactory.validCreateCommentRequestJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        BlogComment comment = mapper.readValue(commentResult.getResponse().getContentAsString(), BlogComment.class);
        assertThat(comment).isNotNull();

        MvcResult allBlogPostsResult = mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<BlogComment> allBlogPosts = mapper.readValue(allBlogPostsResult.getResponse().getContentAsString(), new TypeReference<List<BlogComment>>() {});
        assertThat(allBlogPosts).isNotNull();




//        assertThat(reserveBalance.getAmount()).isNotNull();
//        assertThat(reserveBalance.getAmount().getAmount()).isEqualTo(10.0f);
//        assertThat(reserveBalance.getBucket()).isNotNull();
//        assertThat(reserveBalance.getBucket().getId()).isNull();
//        assertThat(reserveBalance.getLogicalResource()).hasSize(1);
//        LogicalResourceRef logicalResource = reserveBalance.getLogicalResource().get(0);
//        assertThat(logicalResource).isNotNull();
//        assertThat(logicalResource.getId()).isEqualTo("447867536287");
//        assertThat(logicalResource.getName()).isEqualTo("MSISDN");
//        assertThat(reserveBalance.getPartyAccount()).isNotNull();
//        assertThat(reserveBalance.getPartyAccount().getId()).isEqualTo("447867536287");
//        assertThat(reserveBalance.getStatus()).isEqualTo(ActionStatusType.CREATED);
//        assertThat(reserveBalance.getUsageType()).isEqualTo(UsageType.MONETARY);
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