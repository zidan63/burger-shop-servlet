package com.burger.services;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.burger.entities.User;
import com.burger.exception.BaseException;
import com.burger.exception.NotFoundException;
import com.burger.others.AccessToken;
import com.burger.repositories.UserRepository;
import com.google.gson.Gson;

public class AuthService extends BaseService<User> {
  private static AuthService instance;
  private final UserRepository userRepository;
  private final Gson gson = new Gson();

  public static AuthService getInstance() {
    if (instance == null)
      instance = new AuthService();
    return instance;
  }

  private AuthService() {
    userRepository = UserRepository.getInstance();
  }

  public AccessToken genarateAccessToken(User user) {
    String accessToken = JWT.create()
        .withPayload(gson.toJson(user))
        .sign(Algorithm.HMAC256("SECRET"));
    return new AccessToken(accessToken);
  }

  public String verifyAccessToken(String accessToken) {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256("SECRET")).build();
    DecodedJWT jwt = verifier.verify(accessToken);
    return decodeToJson(jwt.getPayload());
  }

  public AccessToken login(String username, String password) throws BaseException {

    User user = transaction.doInTransaction(() -> userRepository.findByUserName(username));

    if (user == null) {
      throw new NotFoundException("Tài khoản hoặc mật khẩu không chính xác!");
    }

    boolean isValid = BCrypt.checkpw(password, user.getPassword());
    if (!isValid) {
      throw new NotFoundException("Tài khoản hoặc mật khẩu không chính xác!");
    }

    AccessToken accessToken = genarateAccessToken(user);

    return accessToken;
  }

  public String decodeToJson(final String base64) {
    return StringUtils.newStringUtf8(Base64.decodeBase64(base64));
  }
}
