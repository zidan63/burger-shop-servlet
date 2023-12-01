package com.burger.repositories;

import com.burger.entities.Topping;

public class ToppingRepository extends BaseRepository<Topping> {
  private static ToppingRepository instance;

  private ToppingRepository() {
    super(Topping.class);
  }

  public static ToppingRepository getInstance() {
    if (instance == null)
      instance = new ToppingRepository();
    return instance;
  }

}
