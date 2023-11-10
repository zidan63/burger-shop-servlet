package com.burger.services;

import java.util.List;

import com.burger.config.TransactionManager;
import com.burger.entities.BaseEntity;
import com.burger.exception.BaseException;
import com.burger.repositories.BaseRepository;

public class BaseService<T extends BaseEntity, R extends BaseRepository<T>> {
  protected final TransactionManager<T> transaction;
  protected final R repository;

  public BaseService(R repository) {
    transaction = new TransactionManager<T>();
    this.repository = repository;
  }

  public List<T> findAll() {
    return (List<T>) transaction.doInTransaction(repository::findAll);
  }

  public T findById(Integer id) {
    return transaction.doInTransaction(() -> repository.findById(id));
  }

  public T saveOrUpdate(T data) throws BaseException {
    return transaction.doInTransaction(() -> repository.saveOrUpdate(data));
  }

  public void delete(Integer id) {
    transaction.doInTransaction(() -> repository.delete(id));
  }

}
