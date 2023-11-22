package com.burger.repositories;

import com.burger.entities.Supplier;

public class SupplierRepository extends BaseRepository<Supplier> {
  private static SupplierRepository instance;

  private SupplierRepository() {
    super(Supplier.class);
  }

  public static SupplierRepository getInstance() {
    if (instance == null)
      instance = new SupplierRepository();
    return instance;
  }

}
