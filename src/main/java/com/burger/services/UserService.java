package com.burger.services;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.burger.entities.User;
import com.burger.exception.BadRequestException;
import com.burger.exception.BaseException;
import com.burger.repositories.UserRepository;

public class UserService extends BaseService<User, UserRepository> {
  private static UserService instance;

  public static UserService getInstance() {
    if (instance == null)
      instance = new UserService();
    return instance;
  }

  private UserService() {
    super(UserRepository.getInstance());
  }

  public User findByUserName(String username) {
    return transaction.doInTransaction(() -> repository.findByUserName(username));
  }

  public User findByUserNameOrEmail(String username, String email) {
    return transaction.doInTransaction(() -> repository.findByUserNameOrEmail(username, email));
  }

  @Override
  public User saveOrUpdate(User user) throws BaseException {

    if (user.getId() == null) {
      User userExist = findByUserNameOrEmail(user.getUsername(), user.getEmail());
      if (userExist != null) {

        List<String> messages = new ArrayList<>();

        if (user.getUsername().equals(userExist.getUsername()))
          messages.add("Tài khoản đã tồn tại!");

        if (user.getEmail().equals(userExist.getEmail()))
          messages.add("Email đã tồn tại!");

        if (messages.size() > 0)
          throw new BadRequestException(messages);

      }

    }

    if (user.getPassword() != null) {
      String salt = BCrypt.gensalt(10);
      String hashPass = BCrypt.hashpw(user.getPassword(), salt);
      user.setPassword(hashPass);
    }

    return transaction.doInTransaction(() -> repository.saveOrUpdate(user));
  }

}
