package com.duncantait.blog.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String code;
    private String reason;
    private String referenceId;
}
