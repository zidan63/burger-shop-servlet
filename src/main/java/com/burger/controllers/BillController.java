package com.burger.controllers;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;
import com.burger.entities.*;
import com.burger.enums.PermissionCode;
import com.burger.middlewares.AuthMiddleware;
import com.burger.middlewares.PermissionMiddleware;
import com.burger.others.RequestAuth;
import com.burger.others.Search;
import com.burger.others.SearchResult;
import com.burger.others.SerializerResult;
import com.burger.services.BillService;
import com.burger.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet({"/orders", "/bills", "/search/orders"})
@Middlewares({ AuthMiddleware.class, PermissionMiddleware.class })
public class BillController extends BaseController {
  @Override
  @RequirePermissions({PermissionCode.READ_BILL})
  protected void doGet(RequestAuth req, HttpServletResponse resp)
          throws ServletException, IOException {
    String requestURI = req.getServletPath();
    switch (requestURI) {
      case "/bills": {
        System.out.println("GET /bills");
        User user = req.getUserCurrent();
        System.out.println("user : " + (user == null));
        if(user != null) {
          System.out.println("User:" + user.getId()+ " " + user == null);
          List<Bill> result = BillService.getInstance().findByUser(user);
          resp.getWriter().write(SerializerResult.serialize(result));
        }else {
          resp.getWriter().write(SerializerResult.serialize(null));
        }
        break;

      }
      case "/orders": {
        String billId = req.getParameter("id");
        if (billId != null && billId != "") {
          Bill result = BillService.getInstance().findById(Integer.valueOf(billId));
          resp.getWriter().write(gson.toJson(result));
        } else {
          System.out.println("GET /orders");
          List<Bill> result = BillService.getInstance().findAll();
          SearchResult<Bill> sr = new SearchResult<Bill>((long) result.size(),1,result);
          resp.getWriter().write(SerializerResult.serialize(sr));
        }
        break;
      }
      case "/search/orders": {
        Map<String, String[]> map = req.getParameterMap();
        Search search = Search.builder()
                .page(req.getParameter("page"))
                .pageSize(req.getParameter("pageSize"))
                .type(req.getParameter("searchType"))
                .build();

        SearchResult<Bill> result = BillService.getInstance().findByFields(search, map);
        resp.getWriter().write(SerializerResult.serialize(result));
        break;
      }
    }
  }


  @Override
  @RequirePermissions({PermissionCode.CREATE_BILL})
  protected void doPost(RequestAuth req, HttpServletResponse resp)
          throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Address address = gson.fromJson(reader, Address.class);
    User user = User.builder().id(req.getUserCurrent().getId()).build();
    Bill result = BillService.getInstance().createBill(user, address,Status.PENDING);
    resp.getWriter().write(gson.toJson(result));
  }

  @Override
  @RequirePermissions({PermissionCode.UPDATE_BILL})
  protected void doPut(RequestAuth req, HttpServletResponse resp)
          throws ServletException, IOException {
    BufferedReader reader = req.getReader();
    Bill mapped = gson.fromJson(reader, Bill.class);
    Bill bill = BillService.getInstance().findById(mapped.getId());
    if(bill.getStatus() == Status.PENDING) {
      User user = User.builder().id(req.getUserCurrent().getId()).build();
      bill.setStatus(mapped.getStatus());
      bill.setEmployee(user);
      BillService.getInstance().saveOrUpdate(bill);
      resp.getWriter().write(SerializerResult.serialize(bill));
    }else {
      resp.getWriter().write(SerializerResult.serialize(null));
    }

  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof BillController)) return false;
    final BillController other = (BillController) o;
    if (!other.canEqual((Object) this)) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof BillController;
  }

  public int hashCode() {
    int result = 1;
    return result;
  }
}
