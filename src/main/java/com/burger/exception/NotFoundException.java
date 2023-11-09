package com.burger.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotFoundException extends BaseException {

  public NotFoundException(String message) {
    super(HttpServletResponse.SC_NOT_FOUND, message);
  }

}