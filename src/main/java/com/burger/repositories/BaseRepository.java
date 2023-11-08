package com.burger.repositories;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.burger.config.HibernateInitialize;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

class BaseRepository<T> {
  protected final SessionFactory factory;
  private final Class<T> type;

  protected BaseRepository(Class<T> type) {
    this.type = type;
    factory = HibernateInitialize.getSessionFactory();
  }

  public T findOne(Integer id) {
    return factory.getCurrentSession().get(type, id);
  }

  public List<T> findAll() {
    Session session = factory.getCurrentSession();
    CriteriaQuery<T> query = getCriteriaQuery(session);
    Root<T> root = query.from(type);
    query.select(root);
    return session.createQuery(query).getResultList();
  }

  // public List<T> findByFields(List<SearchMap> searchMap) {
  // Session session = factory.getCurrentSession();
  // CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
  // CriteriaQuery<T> criteria = getCriteriaQuery(session);
  // Root<T> root = criteria.from(type);

  // criteria.select(root);
  // searchMap.forEach(search -> criteria.where(
  // search.getField() instanceof String ?
  // criteriaBuilder.and(criteriaBuilder.like(
  // root.get(search.getField()),
  // search.getValue().toString()))
  // : criteriaBuilder.and(criteriaBuilder.equal(
  // root.get(search.getField()),
  // search.getValue()))));

  // TypedQuery<T> query = session.createQuery(criteria);
  // return query.getResultList();
  // }

  public T saveOrUpdate(T data) {
    return factory.getCurrentSession().merge(data);
  }

  public void delete(Integer id) {
    T temp = this.findOne(id);
    factory.getCurrentSession().remove(temp);
  }

  protected CriteriaQuery<T> getCriteriaQuery(Session session) {
    return session.getCriteriaBuilder().createQuery(type);
  }

}