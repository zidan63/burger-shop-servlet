package com.burger.config;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.burger.exception.BadRequestException;
import com.burger.exception.BaseException;
import com.burger.others.SearchResult;

public class TransactionManager<T> {

  private final SessionFactory factory;
  protected final Logger logger = LogManager.getLogger(this);

  public TransactionManager() {
    this.factory = HibernateInitialize.getSessionFactory();
  }

  public List<T> doInTransaction(GetList<T> test) throws BaseException {
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
      logger.error("", e);
      throw new BadRequestException(e.toString());

    } finally {
      if (session != null)
        session.close();
    }
  }

  public SearchResult<T> doInTransaction(GetSearchResult<T> test) throws BaseException {
    Transaction transaction = null;
    Session session = factory.getCurrentSession();
    try {
      transaction = session.beginTransaction();
      SearchResult<T> result = test.execute();
      transaction.commit();
      return result;
    } catch (Exception e) {
      if (transaction != null)
        transaction.rollback();
      logger.error("", e);
      throw new BadRequestException(e.toString());

    } finally {
      if (session != null)
        session.close();
    }

  }

  public T doInTransaction(Get<T> test) throws BaseException {
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
      logger.error("", e);
      throw new BadRequestException(e.toString());

    } finally {
      if (session != null)
        session.close();
    }

  }

  public void doInTransaction(IExecute execute) throws BaseException {
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
      logger.error("", e);
      throw new BadRequestException(e.toString());

    } finally {
      if (session != null)
        session.close();
    }
  }

  public interface GetList<E> {
    List<E> execute();
  }

  public interface GetSearchResult<E> {
    SearchResult<E> execute();
  }

  public interface Get<E> {
    E execute() throws IOException;
  }

  public interface IExecute {
    void execute();
  }
}