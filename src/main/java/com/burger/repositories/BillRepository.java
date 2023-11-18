package com.burger.repositories;

import java.util.List;

import org.hibernate.Session;

import com.burger.entities.Bill;
import com.burger.entities.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class BillRepository extends BaseRepository<Bill> {
  private static BillRepository instance;

  private BillRepository() {
    super(Bill.class);
  }

  public static BillRepository getInstance() {
    if (instance == null)
      instance = new BillRepository();
    return instance;
  }

  public List<Bill> findByUser(User user) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<Bill> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(
        criteriaBuilder.equal(root.get("user"), user));

    return session.createQuery(criteriaQuery).getResultList();
  }

}