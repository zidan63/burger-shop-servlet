package com.burger.repositories;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import com.burger.config.HibernateInitialize;
import com.burger.others.SearchMap;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

class BaseRepository<T> {
  protected final SessionFactory factory;
  protected final Class<T> type;

  protected BaseRepository(Class<T> type) {
    this.type = type;
    factory = HibernateInitialize.getSessionFactory();
  }

  public T findOne(Integer id) {
    return factory.getCurrentSession().get(type, id);
  }

  public List<T> findAll() {
    Session session = factory.getCurrentSession();
    CriteriaQuery<T> criteriaQuery = getCriteriaQuery(session);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    return session.createQuery(criteriaQuery).getResultList();
  }

  public List<T> findByFields(List<SearchMap> searchMaps) {
    Session session = factory.getCurrentSession();
    CriteriaQuery<T> criteriaQuery = getCriteriaQuery(session);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

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

  public void delete(Integer id) {
    T temp = this.findOne(id);
    factory.getCurrentSession().remove(temp);
  }

  protected CriteriaQuery<T> getCriteriaQuery(Session session) {
    return session.getCriteriaBuilder().createQuery(type);
  }

}