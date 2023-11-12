package com.burger.repositories;

import com.burger.entities.Suplier;

public class SuplierRepository extends BaseRepository<Suplier> {
  private static SuplierRepository instance;

  private SuplierRepository() {
    super(Suplier.class);
  }

  public static SuplierRepository getInstance() {
    if (instance == null)
      instance = new SuplierRepository();
    return instance;
  }

}
