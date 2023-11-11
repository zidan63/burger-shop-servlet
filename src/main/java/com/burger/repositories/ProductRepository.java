package com.burger.repositories;

import com.burger.entities.Product;

public class ProductRepository extends BaseRepository<Product> {
  private static ProductRepository instance;

  private ProductRepository() {
    super(Product.class);
  }

  public static ProductRepository getInstance() {
    if (instance == null)
      instance = new ProductRepository();
    return instance;
  }

}
