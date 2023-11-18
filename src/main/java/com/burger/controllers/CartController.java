package com.burger.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.CartItem;
import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.services.CartService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cart")
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class CartController extends BaseController {
  @Override
  @RequirePermissions({ PermissionCode.READ_CART })
  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    User user = req.getUserCurrent();
    List<CartItem> result = CartService.getInstance().findByUser(user);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.CREATE_CART })
  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    CartItem cartItem = gson.fromJson(reader, CartItem.class);
    cartItem.setUser(req.getUserCurrent());
    CartItem result = CartService.getInstance().addCartItem(cartItem);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.UPDATE_CART })
  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    CartItem cartItem = gson.fromJson(reader, CartItem.class);
    cartItem.setUser(req.getUserCurrent());
    CartItem result = CartService.getInstance().updateCartItem(cartItem);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({ PermissionCode.DELETE_CART })
  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    Integer id = Integer.valueOf(req.getParameter("id"));
    CartService.getInstance().delete(id);
  }

}
