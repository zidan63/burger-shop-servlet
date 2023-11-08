package com.burger.services;

import com.burger.config.TransactionManager;

public class BaseService<T> {
  protected final TransactionManager<T> transaction;

  public BaseService() {
    transaction = new TransactionManager<T>();
  }

}
