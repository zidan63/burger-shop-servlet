package com.burger.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.burger.entities.User;
import com.burger.repositories.UserRepository;

public class UserService extends BaseService<User> {
  private static UserService instance;
  private final UserRepository userRepository;

  public static UserService getInstance() {
    if (instance == null)
      instance = new UserService();
    return instance;
  }

  private UserService() {
    userRepository = UserRepository.getInstance();
  }

  public List<User> findAll() {
    return (List<User>) transaction.doInTransaction(userRepository::findAll);
  }

  public User findOne(Integer id) {
    return transaction.doInTransaction(() -> userRepository.findOne(id));
  }

  // public List<User> findByFields(List<SearchMap> searchMap) {
  // return (List<User>) transaction.doInTransaction(() ->
  // userRepository.findByFields(searchMap));
  // }

  public User saveOrUpdate(User user) {
    String salt = BCrypt.gensalt(10);
    String hashPass = BCrypt.hashpw(user.getPassword(), salt);
    user.setPassword(hashPass);

    return transaction.doInTransaction(() -> userRepository.saveOrUpdate(user));
  }

  public void delete(Integer id) {
    transaction.doInTransaction(() -> userRepository.delete(id));
  }
}
