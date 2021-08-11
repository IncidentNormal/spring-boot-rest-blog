package com.duncantait.blog.util;

import com.duncantait.blog.controller.model.BlogCommentRequest;
import com.duncantait.blog.controller.model.BlogPostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class RequestFactory {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String validCreateBlogPostRequestJson() throws JsonProcessingException {
        BlogPostRequest request = new BlogPostRequest();
        request.setTitle("title");
        request.setTags(List.of("tag1", "tag2", "tag3"));
        request.setBody("some text some text blah blah 123....1!");

        return mapper.writeValueAsString(request);
    }

    public static String validCreateCommentRequestJson() throws JsonProcessingException {
        BlogCommentRequest request = new BlogCommentRequest();
        request.setTitle("comment title");
        request.setBody("a comment some text x y z a b c!");

        return mapper.writeValueAsString(request);
    }
}
