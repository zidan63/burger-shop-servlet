package com.burger.services;

import java.security.SecureRandom;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.burger.entities.Role;
import com.burger.entities.User;
import com.burger.exception.BaseException;
import com.burger.exception.ForbiddenException;
import com.burger.exception.NotFoundException;
import com.burger.others.LoginPayload;
import com.burger.others.PayloadToken;
import com.google.gson.Gson;

public class AuthService {
  private static AuthService instance;
  private final Gson gson = new Gson();
  private final String OTP_CHARACTERS = "0123456789";
  private final int OTP_LENGTH = 6;

  public static AuthService getInstance() {
    if (instance == null)
      instance = new AuthService();
    return instance;
  }

  private AuthService() {
  }

  public String genarateAccessToken(User user) {

    PayloadToken payloadToken = new PayloadToken(user.getId());

    String accessToken = JWT.create()
        .withPayload(gson.toJson(payloadToken))
        .sign(Algorithm.HMAC256("SECRET"));
    return accessToken;
  }

  public String verifyAccessToken(String accessToken) {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256("SECRET")).build();
    DecodedJWT jwt = verifier.verify(accessToken);
    return decodeToJson(jwt.getPayload());
  }

  public String decodeToJson(final String base64) {
    return StringUtils.newStringUtf8(Base64.decodeBase64(base64));
  }

  public LoginPayload login(String username, String password) throws BaseException {

    User user = UserService.getInstance().findByUserNameOrEmail(username, username);

    if (user == null) {
      throw new NotFoundException("Tài khoản hoặc mật khẩu không chính xác!");
    }

    boolean isValid = BCrypt.checkpw(password, user.getPassword());
    if (!isValid) {
      throw new NotFoundException("Tài khoản hoặc mật khẩu không chính xác!");
    }

    String accessToken = genarateAccessToken(user);

    return new LoginPayload(accessToken, user);
  }

  public User register(User user) throws BaseException {
    Role role = RoleService.getInstance().findById(3);
    user.setRole(role);
    return UserService.getInstance().saveOrUpdate(user);
  }

  public void forget(User user) throws BaseException {
    String username = user.getUsername();
    User userResult = UserService.getInstance().findByUserNameOrEmail(username, username);

    if (userResult == null) {
      throw new NotFoundException("Tài khoản không tồn tại!");
    }

    userResult.setOTP(generateOTP());

    UserService.getInstance().saveOrUpdate(userResult);

    EmailService.getInstance().sendPlainTextEmail(userResult.getEmail(),
        "Quên mật khẩu",
        List.of("Mã xác thực của bạn là: ", userResult.getOTP()),
        false);

  }

  private String generateOTP() {
    SecureRandom secureRandom = new SecureRandom();
    StringBuilder otp = new StringBuilder(OTP_LENGTH);
    for (int i = 0; i < OTP_LENGTH; i++) {
      int index = secureRandom.nextInt(OTP_CHARACTERS.length());
      char randomChar = OTP_CHARACTERS.charAt(index);
      otp.append(randomChar);
    }
    return otp.toString();
  }

  public void otp(User user) throws BaseException {
    String username = user.getUsername();
    User userResult = UserService.getInstance().findByUserNameOrEmail(username, username);

    if (userResult == null) {
      throw new NotFoundException("Tài khoản không tồn tại!");
    }

    if (!user.getOTP().equals(userResult.getOTP())) {
      throw new ForbiddenException("Mã xác thực không chính xác!");
    }

    userResult.setPassword(user.getPassword());

    UserService.getInstance().saveOrUpdate(userResult);

  }

}
