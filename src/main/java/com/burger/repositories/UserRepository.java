package com.burger.repositories;

import com.burger.entities.User;

public class UserRepository extends BaseRepository<User> {
  private static UserRepository instance;

  private UserRepository() {
    super(User.class);
  }

  public static UserRepository getInstance() {
    if (instance == null)
      instance = new UserRepository();
    return instance;
  }

}
