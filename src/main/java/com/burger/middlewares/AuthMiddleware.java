package com.burger.middlewares;

import com.burger.entities.User;
import com.burger.exception.BaseException;
import com.burger.exception.UnauthorizedException;
import com.burger.others.PayloadToken;
import com.burger.others.RequestAuth;
import com.burger.services.AuthService;
import com.burger.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

public class AuthMiddleware extends BaseMiddleware {

  @Override
  public void handle(RequestAuth req, HttpServletResponse resp) throws BaseException {
    String authorizationHeader = req.getHeader("Authorization");

    if (req.getPermissionCode() == null) {
      return;
    }

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String accessToken = authorizationHeader.substring(7); // Bỏ qua "Bearer "
      logger.info("accessToken: " + accessToken);

      try {
        String payloadJson = AuthService.getInstance().verifyAccessToken(accessToken);
        PayloadToken userPayload = gson.fromJson(payloadJson, PayloadToken.class);

        User user = UserService.getInstance().findById(userPayload.getId());

        if (user == null) {
          throw new UnauthorizedException("Vui lòng đăng nhập!");
        }

        req.setUserCurrent(user);

      } catch (Exception e) {
        throw new UnauthorizedException("Vui lòng đăng nhập!");
      }

      return;

    } else {
      throw new UnauthorizedException("Vui lòng đăng nhập!");
    }

  }

}
