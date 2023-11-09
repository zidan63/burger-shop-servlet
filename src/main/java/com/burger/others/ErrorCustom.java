package com.burger.others;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorCustom {
  private int status;
  private String message;
  private List<String> messages;
}
