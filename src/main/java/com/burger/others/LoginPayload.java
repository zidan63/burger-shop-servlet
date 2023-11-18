package com.burger.others;

import com.burger.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPayload {
  private String accessToken;
  private User user;
}
