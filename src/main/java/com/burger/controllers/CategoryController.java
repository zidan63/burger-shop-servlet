package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.Category;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.others.Search;
import com.burger.others.SearchResult;
import com.burger.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/categories")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class CategoryController extends BaseController {
  @Override
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<String, String[]> map = req.getParameterMap();
    Search search = Search.builder()
        .page(req.getParameter("page"))
        .pageSize(req.getParameter("pageSize"))
        .type(req.getParameter("searchType"))
        .build();

    SearchResult<Category> result = CategoryService.getInstance().findByFields(search, map);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_CATEGORY })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Category user = gson.fromJson(reader, Category.class);
    Category result = CategoryService.getInstance().saveOrUpdate(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_CATEGORY })
  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Category user = gson.fromJson(reader, Category.class);
    Category result = CategoryService.getInstance().saveOrUpdate(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.DELETE_CATEGORY })
  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Integer id = Integer.valueOf(req.getParameter("id"));
    CategoryService.getInstance().delete(id);
  }

}
