package com.burger.services;

import com.burger.entities.Role;
import com.burger.repositories.RoleRepository;

public class RoleService extends BaseService<Role, RoleRepository> {

  private static RoleService instance;

  public static RoleService getInstance() {
    if (instance == null)
      instance = new RoleService();
    return instance;
  }

  private RoleService() {
    super(RoleRepository.getInstance());
  }

}
