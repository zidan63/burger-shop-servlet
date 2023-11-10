package com.burger.repositories;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.burger.config.HibernateInitialize;
import com.burger.entities.BaseEntity;
import com.burger.others.SearchMap;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BaseRepository<T extends BaseEntity> {
  protected final SessionFactory factory;
  protected final Class<T> type;

  protected BaseRepository(Class<T> type) {
    this.type = type;
    factory = HibernateInitialize.getSessionFactory();
  }

  public T findById(Integer id) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(
        criteriaBuilder.equal(root.get("id"), id));

    return session.createQuery(criteriaQuery).uniqueResult();
  }

  public List<T> findAll() {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    return session.createQuery(criteriaQuery).getResultList();
  }

  public List<T> findByFields(List<SearchMap> searchMaps) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    for (SearchMap searchMap : searchMaps) {
      if (searchMap.getField() instanceof String) {

        Path<String> fieldPath = root.get(searchMap.getField());
        String value = searchMap.getValue().toString();
        Predicate like = criteriaBuilder.like(fieldPath, value);

        criteriaQuery.where(criteriaBuilder.and(like));
      } else {
        Path<String> fieldPath = root.get(searchMap.getField());
        Object value = searchMap.getValue();
        Predicate equal = criteriaBuilder.equal(fieldPath, value);

        criteriaQuery.where(criteriaBuilder.and(equal));
      }
    }

    return session.createQuery(criteriaQuery).getResultList();
  }

  public T saveOrUpdate(T data) {
    return factory.getCurrentSession().merge(data);
  }

  public Integer delete(Integer id) {
    T data = findById(id);

    if (data != null) {
      data.setDeletedAt(new Date());
      saveOrUpdate(data);
    }

    return id;
  }

}