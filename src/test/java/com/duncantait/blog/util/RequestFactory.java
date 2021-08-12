package com.duncantait.blog.util;

import com.duncantait.blog.controller.model.BlogCommentRequest;
import com.duncantait.blog.controller.model.BlogPostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class RequestFactory {

    private static final List<String> DEFAULT_TAGS =  List.of("tag1", "tag2", "tag3");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String validCreateBlogPostRequestJson(String... tags) throws JsonProcessingException {
        List<String> tagList = (tags.length == 0) ? DEFAULT_TAGS : List.of(tags);

        BlogPostRequest request = new BlogPostRequest();
        request.setTitle("title");
        request.setTags(tagList);
        request.setBody("some text some text blah blah 123....1!");

        return MAPPER.writeValueAsString(request);
    }

    public static String validCreateCommentRequestJson() throws JsonProcessingException {
        BlogCommentRequest request = new BlogCommentRequest();
        request.setTitle("comment title");
        request.setBody("a comment some text x y z a b c!");

        return MAPPER.writeValueAsString(request);
    }
}
