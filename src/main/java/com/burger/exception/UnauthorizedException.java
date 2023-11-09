package com.burger.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UnauthorizedException extends BaseException {

  public UnauthorizedException(String message) {
    super(HttpServletResponse.SC_UNAUTHORIZED, message);
  }

}