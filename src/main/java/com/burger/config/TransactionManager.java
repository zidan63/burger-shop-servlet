package com.burger.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class TransactionManager<T> {

  private final SessionFactory factory;

  public TransactionManager() {
    this.factory = HibernateInitialize.getSessionFactory();
  }

  public Collection<T> doInTransaction(GetList<T> test) {
    Transaction transaction = null;
    Session session = factory.getCurrentSession();
    try {
      transaction = session.beginTransaction();
      List<T> result = test.execute();
      transaction.commit();
      return result;
    } catch (Exception e) {
      if (transaction != null)
        transaction.rollback();

    } finally {
      if (session != null)
        session.close();
    }
    return null;
  }

  public T doInTransaction(Get<T> test) {
    Transaction transaction = null;
    Session session = null;
    try {
      session = factory.getCurrentSession();
      transaction = session.beginTransaction();
      T result = test.execute();
      transaction.commit();
      return result;
    } catch (Exception e) {
      if (transaction != null)
        transaction.rollback();

    } finally {
      if (session != null)
        session.close();
    }
    return null;
  }

  public void doInTransaction(IExecute execute) {
    Transaction transaction = null;
    Session session = null;
    try {
      session = factory.getCurrentSession();
      transaction = session.beginTransaction();
      execute.execute();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null)
        transaction.rollback();

    } finally {
      if (session != null)
        session.close();
    }
  }

  public interface GetList<E> {
    List<E> execute();
  }

  public interface Get<E> {
    E execute() throws IOException;
  }

  public interface IExecute {
    void execute();
  }
}