package com.duncantait.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPost {
    Long id;
    LocalDateTime creationDate;
    String title;
    String body;
    @Builder.Default
    Set<BlogTag> tags = Set.of();
}
