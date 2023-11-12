package com.burger.repositories;

import com.burger.entities.Permission;

public class PermissionRepository extends BaseRepository<Permission> {
  private static PermissionRepository instance;

  private PermissionRepository() {
    super(Permission.class);
  }

  public static PermissionRepository getInstance() {
    if (instance == null)
      instance = new PermissionRepository();
    return instance;
  }

}
