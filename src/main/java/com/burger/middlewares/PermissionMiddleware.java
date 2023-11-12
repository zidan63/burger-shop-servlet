package com.burger.middlewares;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.burger.entities.Permission;
import com.burger.entities.User;
import com.burger.enums.PermissionCode;
import com.burger.exception.BaseException;
import com.burger.exception.ForbiddenException;
import com.burger.others.RequestAuth;

import jakarta.servlet.http.HttpServletResponse;

public class PermissionMiddleware extends BaseMiddleware {

  @Override
  public void handle(RequestAuth req, HttpServletResponse resp) throws BaseException {

    if (req.getPermissionCode() == null) {
      return;
    }

    User user = req.getUserCurrent();
    try {
      Set<Permission> userPermissions = user.getRole().getPermissions();

      Set<PermissionCode> userPermissionCodes = userPermissions.stream()
          .map(Permission::getCode)
          .collect(Collectors.toSet());

      List<PermissionCode> requestedPermissionCodes = req.getPermissionCode();

      // Kiểm tra xem người dùng có tất cả các quyền trong danh sách yêu cầu hay không
      boolean hasAllPermissions = userPermissionCodes.containsAll(requestedPermissionCodes);

      if (!hasAllPermissions) {
        // Người dùng không có ít nhất một quyền trong danh sách yêu cầu
        throw new ForbiddenException("Tài khoản không có quyền!");
      }
    } catch (Exception e) {
      throw new ForbiddenException("Tài khoản không có quyền!");
    }

  }

}
