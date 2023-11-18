package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import com.burger.entities.User;
import com.burger.exception.BaseException;
import com.burger.others.LoginPayload;
import com.burger.others.RequestAuth;
import com.burger.services.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/auth/login", "/auth/register", "/auth/forget", "/auth/otp" })
public class AuthController extends BaseController {
  @Override
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {

    String requestURI = req.getServletPath();
    String[] parts = requestURI.split("/");

    if (parts.length >= 3) {
      String slug = parts[2];
      logger.info(slug);

      BufferedReader reader = req.getReader();
      User user = gson.fromJson(reader, User.class);

      switch (slug) {
        case "login":
          login(resp, user);
          break;

        case "register":
          register(resp, user);
          break;

        case "forget":
          forget(resp, user);
          break;

        case "otp":
          otp(resp, user);
          break;

      }
    }
  }

  private void login(HttpServletResponse resp, User user)
      throws BaseException, IOException {
    LoginPayload result = AuthService.getInstance().login(user.getUsername(), user.getPassword());
    resp.getWriter().write(gson.toJson(result));
  }

  private void register(HttpServletResponse resp, User user)
      throws BaseException, IOException {

    User result = AuthService.getInstance().register(user);
    resp.getWriter().write(gson.toJson(result));

  }

  private void forget(HttpServletResponse resp, User user)
      throws BaseException, IOException {

    AuthService.getInstance().forget(user);
    resp.getWriter().write(gson.toJson("Kiểm tra email"));

  }

  private void otp(HttpServletResponse resp, User user)
      throws BaseException, IOException {

    AuthService.getInstance().otp(user);
    resp.getWriter().write(gson.toJson("Đã đổi mật khẩu thành công"));
  }
}
