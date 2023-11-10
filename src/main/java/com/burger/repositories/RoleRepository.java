package com.burger.repositories;

import com.burger.entities.Role;

public class RoleRepository extends BaseRepository<Role> {
  private static RoleRepository instance;

  private RoleRepository() {
    super(Role.class);
  }

  public static RoleRepository getInstance() {
    if (instance == null)
      instance = new RoleRepository();
    return instance;
  }

}
