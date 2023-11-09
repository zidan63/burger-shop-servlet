package com.burger.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ForbiddenException extends BaseException {

  public ForbiddenException(String message) {
    super(HttpServletResponse.SC_FORBIDDEN, message);
  }

}
