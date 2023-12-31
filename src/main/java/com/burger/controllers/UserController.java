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

@WebServlet("/users")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class UserController extends BaseController {

  @Override
  @RequirePermissions({ PermissionCode.READ_USER })
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

  @Override
  @RequirePermissions({ PermissionCode.CREATE_USER })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    User user = gson.fromJson(reader, User.class);
    User result = UserService.getInstance().saveOrUpdate(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_USER })
  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    System.out.println("asdsadsad");
    User mapped = gson.fromJson(reader, User.class);
    User user = req.getUserCurrent();

    user.setFullName(mapped.getFullName());
    user.setEmail(mapped.getEmail());
    System.out.println("Update fullname: "+mapped.getFullName());
    User result = UserService.getInstance().saveOrUpdate(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_USER })
  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Integer id = Integer.valueOf(req.getParameter("id"));
    UserService.getInstance().delete(id);
  }

}
