package com.burger.exception;

import java.util.List;

import jakarta.servlet.ServletException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends ServletException {
  private int status;
  private String message;
  private List<String> messages;

  public BaseException(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public BaseException(int status, List<String> messages) {
    this.status = status;
    this.messages = messages;
  }

}