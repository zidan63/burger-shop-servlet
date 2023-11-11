package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.others.Search;
import com.burger.others.SearchResult;
import com.burger.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/roles")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class RoleController extends BaseController {
  @Override
  @RequirePermissions({ PermissionCode.READ_ROLE })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<String, String[]> map = req.getParameterMap();
    Search search = Search.builder()
        .page(req.getParameter("page"))
        .pageSize(req.getParameter("pageSize"))
        .type(req.getParameter("searchType"))
        .build();

    SearchResult<User> result = UserService.getInstance().findByFields(search, map);
    resp.getWriter().write(gson.toJson(result));
  }
}
