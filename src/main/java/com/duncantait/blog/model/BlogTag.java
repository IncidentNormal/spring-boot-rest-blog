package com.duncantait.blog.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@Builder
public class BlogTag {

    private String name;

    public BlogTag(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("tag name cannot be null");
        }
        this.name = name;
    }


}
