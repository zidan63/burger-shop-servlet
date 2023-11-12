package com.burger.controllers;

import java.io.IOException;
import java.util.List;

import com.burger.annotation.RequirePermissions;
import com.burger.entities.Permission;
import com.burger.enums.PermissionCode;
import com.burger.others.RequestAuth;
import com.burger.services.PermissionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/permission")
public class PermissionController extends BaseController {
  @Override
  @RequirePermissions({ PermissionCode.READ_ROLE })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<Permission> result = PermissionService.getInstance().findAll();
    resp.getWriter().write(gson.toJson(result));
  }
}