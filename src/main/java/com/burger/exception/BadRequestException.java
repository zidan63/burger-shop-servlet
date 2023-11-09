package com.burger.exception;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

public class BadRequestException extends BaseException {

  public BadRequestException(String message) {
    super(HttpServletResponse.SC_BAD_REQUEST, message);
  }

  public BadRequestException(List<String> messages) {
    super(HttpServletResponse.SC_BAD_REQUEST, messages);
  }

}
