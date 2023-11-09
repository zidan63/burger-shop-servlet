package com.burger.exception;

import jakarta.servlet.http.HttpServletResponse;

public class MethodNotAllowedException extends BaseException {

  public MethodNotAllowedException(String message) {
    super(HttpServletResponse.SC_METHOD_NOT_ALLOWED, message);
  }

}
