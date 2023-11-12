package com.burger.repositories;

import com.burger.entities.Category;

public class CategoryRepository extends BaseRepository<Category> {
  private static CategoryRepository instance;

  private CategoryRepository() {
    super(Category.class);
  }

  public static CategoryRepository getInstance() {
    if (instance == null)
      instance = new CategoryRepository();
    return instance;
  }

}
