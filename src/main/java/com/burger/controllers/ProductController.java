package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.Product;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.others.Search;
import com.burger.others.SearchResult;
import com.burger.services.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class ProductController extends BaseController {
  @Override
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<String, String[]> map = req.getParameterMap();
    Search search = Search.builder()
        .page(req.getParameter("page"))
        .pageSize(req.getParameter("pageSize"))
        .type(req.getParameter("searchType"))
        .build();

    SearchResult<Product> result = ProductService.getInstance().findByFields(search, map);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_PRODUCT })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Product product = gson.fromJson(reader, Product.class);
    product.setUser(req.getUserCurrent());
    Product result = ProductService.getInstance().saveOrUpdate(product);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_PRODUCT })
  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Product product = gson.fromJson(reader, Product.class);
    Product result = ProductService.getInstance().saveOrUpdate(product);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.DELETE_PRODUCT })
  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Integer id = Integer.valueOf(req.getParameter("id"));
    ProductService.getInstance().delete(id);
  }

}
