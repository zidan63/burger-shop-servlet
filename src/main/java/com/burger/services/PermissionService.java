package com.burger.services;

import com.burger.entities.Permission;
import com.burger.repositories.PermissionRepository;

public class PermissionService extends BaseService<Permission, PermissionRepository> {

  private static PermissionService instance;

  public static PermissionService getInstance() {
    if (instance == null)
      instance = new PermissionService();
    return instance;
  }

  private PermissionService() {
    super(PermissionRepository.getInstance());
  }

}
