package com.duncantait.blog.controller;

import com.duncantait.blog.controller.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdviceHandler {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @ExceptionHandler(Exception.class)
  public void handleExceptions(final @Valid Exception ex, final HttpServletResponse response) throws IOException {
    log(ex);
    final ErrorResponse errorResponse = from(ex);
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json");
    response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleExceptions(final RuntimeException ex) {
    log(ex);
    ErrorResponse response = from(ex);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private static ErrorResponse from(Exception ex) {
    return ErrorResponse.builder()
            .code("UNHANDLED_ERROR")
            .reason(ex.getMessage())
            .referenceId(UUID.randomUUID().toString())
            .build();
  }

  private void log(final @Valid Exception ex) {
    log.error("Throwing ", ex);
  }
}

