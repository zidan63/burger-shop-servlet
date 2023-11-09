package com.burger.middlewares;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.burger.exception.BaseException;
import com.burger.others.RequestAuth;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseMiddleware {
  protected final Logger logger = LogManager.getLogger(this);
  protected final Gson gson = new Gson();

  public abstract void handle(RequestAuth req, HttpServletResponse resp) throws BaseException;
}
