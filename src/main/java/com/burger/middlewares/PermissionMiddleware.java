package com.burger.middlewares;

import java.util.List;

import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.exception.BaseException;
import com.burger.exception.ForbiddenException;
import com.burger.others.RequestAuth;
import jakarta.servlet.http.HttpServletResponse;

public class PermissionMiddleware extends BaseMiddleware {

  @Override
  public void handle(RequestAuth req, HttpServletResponse resp) throws BaseException {
    User user = req.getUserCurrent();
    // if (req.getUserCurrent() == null) {
    // throw new ForbiddenException("Bạn không có quyền!");
    // }

    List<PermissionCode> list = req.getPermissionCode();
    list.forEach(c -> logger.info(c));
  }

}
