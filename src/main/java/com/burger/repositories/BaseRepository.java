package com.burger.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.burger.config.HibernateInitialize;
import com.burger.entities.BaseEntity;
import com.burger.entities.Product;
import com.burger.entities.Topping;
import com.burger.enums.SearchAboutType;
import com.burger.enums.SearchFieldType;
import com.burger.enums.SearchType;
import com.burger.others.Search;
import com.burger.others.SearchAbout;
import com.burger.others.SearchField;
import com.burger.others.SearchResult;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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

  public SearchResult<T> findByFields(Search search, List<SearchField> searchFields, List<SearchAbout> searchAbouts) {

    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    List<Predicate> predicates = new ArrayList<>();

    for (SearchField searchField : searchFields) {
      Predicate predicate;

      if (searchField.isEmpty()) {
        continue;
      }

      if (searchField.getType() == SearchFieldType.STRING) {

        if (searchField.getValueString() == null) {
          continue;
        }

        Path<String> fieldPath = root.get(searchField.getField());
        String value = searchField.getValueString();
        predicate = criteriaBuilder.like(fieldPath, "%" + value + "%");
      } else if (searchField.getType() == SearchFieldType.NUMBER) {

        if (searchField.getValueNumber() == null) {
          continue;
        }

        Path<Integer> fieldPath = root.get(searchField.getField());
        Integer value = searchField.getValueNumber();
        predicate = criteriaBuilder.equal(fieldPath, value);
      } else if(searchField.getType() == SearchFieldType.ARRAY) {
        if (searchField.getValuesNumber() == null) {
          continue;
        }

        Object[] values = searchField.getValuesNumber();
        predicate = root.get(searchField.getField()).in(values);
      }
      else {
        if (searchField.getValuesNumber() == null) {
          continue;
        }

        Object[] values = searchField.getValuesNumber();
        
        predicate = root.join(searchField.getField()).get("id").in(values);
      }
      predicates.add(predicate);
    }

    for (SearchAbout searchAbout : searchAbouts) {

      if (searchAbout.getType() == SearchAboutType.DATE) {

        if (searchAbout.getFromDate() != null && searchAbout.getToDate() != null) {

          predicates.add(criteriaBuilder
              .between(
                  root.get(searchAbout.getField()),
                  searchAbout.getFromDate(), searchAbout.getToDate()));

        } else if (searchAbout.getFromDate() != null) {

          predicates.add(criteriaBuilder
              .greaterThanOrEqualTo(
                  root.get(searchAbout.getField()),
                  searchAbout.getFromDate()));

        } else if (searchAbout.getToDate() != null) {

          predicates.add(criteriaBuilder
              .lessThanOrEqualTo(
                  root.get(searchAbout.getField()),
                  searchAbout.getToDate()));

        }

      } else {

        if (searchAbout.getFromDate() != null && searchAbout.getToDate() != null) {
          predicates.add(criteriaBuilder.between(
              root.get(searchAbout.getField()),
              searchAbout.getFromInteger(), searchAbout.getToInteger()));

        } else if (searchAbout.getFromInteger() != null)
          predicates.add(criteriaBuilder
              .greaterThanOrEqualTo(
                  root.get(searchAbout.getField()),
                  searchAbout.getFromInteger()));

        else if (searchAbout.getFromInteger() != null)
          predicates.add(criteriaBuilder
              .greaterThanOrEqualTo(
                  root.get(searchAbout.getField()),
                  searchAbout.getToInteger()));
      }

    }

    int start = (search.getPage() - 1) * search.getPageSize();

    if (search.getType() == SearchType.ADVANCED && predicates.size() > 0)
      criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    else if (predicates.size() > 0)
      criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));

    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("updatedAt")));

    TypedQuery<T> query = session.createQuery(criteriaQuery);
    Long totalRecord = query.getResultStream().distinct().count();

    query.setFirstResult(start);
    query.setMaxResults(search.getPageSize());

    return new SearchResult<>(totalRecord, search.getPage(), query.getResultList());
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