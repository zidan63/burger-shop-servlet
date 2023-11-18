package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.Address;
import com.burger.entities.Bill;
import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.services.BillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bills")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class BillController extends BaseController {
  @Override
  @RequirePermissions({ PermissionCode.READ_BILL })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    User user = req.getUserCurrent();
    List<Bill> result = BillService.getInstance().findByUser(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_BILL })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Address address = gson.fromJson(reader, Address.class);
    User user = User.builder().id(req.getUserCurrent().getId()).build();
    Bill result = BillService.getInstance().createBill(user, address);
    resp.getWriter().write(gson.toJson(result));
  }

}
