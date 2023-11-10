package com.burger.controllers;

import java.io.IOException;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/information")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class InformationController extends BaseController {

  @Override
  @RequirePermissions({ PermissionCode.READ_INFO })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().write(gson.toJson(req.getUserCurrent()));
  }
}
