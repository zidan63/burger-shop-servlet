package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import com.burger.entities.User;
import com.burger.services.UserService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class UserController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Gson gson = new Gson();

    User user = gson.fromJson(reader, User.class);
    System.out.println(user.toString());

    User result = UserService.getInstance().saveOrUpdate(user);

    resp.setContentType("application/json");
    resp.setCharacterEncoding("utf-8");
    resp.getWriter().write(gson.toJson(result));
  }

}
