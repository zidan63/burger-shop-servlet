package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.Suplier;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.others.Search;
import com.burger.others.SearchResult;
import com.burger.services.SuplierService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/supliers")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class SuplierController extends BaseController {
  @Override
  @RequirePermissions({ PermissionCode.READ_SUPLIER })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Map<String, String[]> map = req.getParameterMap();
    Search search = Search.builder()
        .page(req.getParameter("page"))
        .pageSize(req.getParameter("pageSize"))
        .type(req.getParameter("searchType"))
        .build();

    SearchResult<Suplier> result = SuplierService.getInstance().findByFields(search, map);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_SUPLIER })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Suplier suplier = gson.fromJson(reader, Suplier.class);
    Suplier result = SuplierService.getInstance().saveOrUpdate(suplier);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_SUPLIER })
  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Suplier suplier = gson.fromJson(reader, Suplier.class);
    Suplier result = SuplierService.getInstance().saveOrUpdate(suplier);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.DELETE_SUPLIER })
  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Integer id = Integer.valueOf(req.getParameter("id"));
    SuplierService.getInstance().delete(id);
  }

}
