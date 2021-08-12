package com.duncantait.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogComment {
    Long id;
    Long postId;
    LocalDateTime creationDate;
    String title;
    String body;
}
