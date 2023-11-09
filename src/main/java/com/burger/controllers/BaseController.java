package com.burger.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.burger.annotation.Middlewares;
import com.burger.annotation.RequirePermissions;

import com.burger.exception.BaseException;
import com.burger.exception.MethodNotAllowedException;
import com.burger.middlewares.BaseMiddleware;
import com.burger.others.ErrorCustom;
import com.burger.others.RequestAuth;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseController extends HttpServlet {

  protected final Logger logger = LogManager.getLogger(this);
  protected final Gson gson = new Gson();

  private static final String METHOD_GET = "GET";
  private static final String METHOD_POST = "POST";
  private static final String METHOD_PUT = "PUT";
  private static final String METHOD_DELETE = "DELETE";

  private void applyMiddlewares(RequestAuth req, HttpServletResponse resp)
      throws Exception {

    for (Method method : this.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(RequirePermissions.class)) {
        RequirePermissions requirePermissions = method.getAnnotation(RequirePermissions.class);
        req.putPermissionCode(Arrays.asList(requirePermissions.value()));
      }
    }

    if (this.getClass().isAnnotationPresent(Middlewares.class)) {
      Middlewares middlewares = this.getClass().getAnnotation(Middlewares.class);

      for (Class<? extends BaseMiddleware> middleware : middlewares.value()) {
        BaseMiddleware middle = middleware.getDeclaredConstructor().newInstance();
        middle.handle(req, resp);
      }

    }
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("utf-8");

    RequestAuth requestAuth = new RequestAuth(req);

    try {
      applyMiddlewares(requestAuth, resp);

      String method = req.getMethod();

      if (method.equals(METHOD_GET)) {
        doGet(requestAuth, resp);
      } else if (method.equals(METHOD_POST)) {
        doPost(requestAuth, resp);
      } else if (method.equals(METHOD_PUT)) {
        doPut(requestAuth, resp);
      } else if (method.equals(METHOD_DELETE)) {
        doDelete(requestAuth, resp);
      } else {
        doElse(requestAuth, resp);
      }
    } catch (BaseException e) {

      ErrorCustom err = ErrorCustom.builder()
          .status(e.getStatus())
          .message(e.getMessage())
          .messages(e.getMessages())
          .build();

      resp.setStatus(e.getStatus());
      resp.getWriter().write(gson.toJson(err));

    } catch (Exception e) {

      ErrorCustom err = ErrorCustom.builder()
          .status(500)
          .message(e.getMessage())
          .build();

      resp.setStatus(500);
      resp.getWriter().write(gson.toJson(err));
    }
  }

  protected void doGet(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new MethodNotAllowedException("Method Not Allowed Exception");
  }

  protected void doPost(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new MethodNotAllowedException("Method Not Allowed Exception");
  }

  protected void doPut(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new MethodNotAllowedException("Method Not Allowed Exception");
  }

  protected void doDelete(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new MethodNotAllowedException("Method Not Allowed Exception");
  }

  private void doElse(RequestAuth req, HttpServletResponse resp)
      throws ServletException, IOException {
    throw new MethodNotAllowedException("Method Not Allowed Exception");
  }
}
