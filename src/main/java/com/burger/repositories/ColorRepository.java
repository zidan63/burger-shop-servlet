package com.burger.repositories;

import com.burger.entities.Color;

public class ColorRepository extends BaseRepository<Color> {
  private static ColorRepository instance;

  private ColorRepository() {
    super(Color.class);
  }

  public static ColorRepository getInstance() {
    if (instance == null)
      instance = new ColorRepository();
    return instance;
  }

}
