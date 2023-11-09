package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Permission;
import java.util.List;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.exception.BadRequestException;
import com.burger.exception.NotFoundException;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class UserController extends BaseController {

  @Override
  @RequirePermissions({ PermissionCode.READ_USER })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Lấy giá trị của một tham số cụ thể từ query string
    String param1 = req.getParameter("notfound");
    String param2 = req.getParameter("badrequest");

    // Kiểm tra xem tham số có tồn tại hay không
    if (param1 != null) {
      throw new NotFoundException("Không tìm thấy người dùng!");
    }

    if (param2 != null) {
      List<String> messages = List.of("Tài khoản đã tồn tại!", "Email đã tồn tại!");
      throw new BadRequestException(messages);
    }

    resp.getWriter().write(gson.toJson("Thành công"));

  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_USER })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    User user = gson.fromJson(reader, User.class);

    logger.info(user.toString());

    User result = UserService.getInstance().saveOrUpdate(user);

    resp.getWriter().write(gson.toJson(result));
  }

}
