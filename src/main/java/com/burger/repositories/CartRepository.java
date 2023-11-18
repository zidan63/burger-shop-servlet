package com.burger.repositories;

import java.util.List;

import org.hibernate.Session;

import com.burger.entities.CartItem;
import com.burger.entities.Product;
import com.burger.entities.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CartRepository extends BaseRepository<CartItem> {
  private static CartRepository instance;

  private CartRepository() {
    super(CartItem.class);
  }

  public static CartRepository getInstance() {
    if (instance == null)
      instance = new CartRepository();
    return instance;
  }

  public List<CartItem> findByUser(User user) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<CartItem> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<CartItem> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(
        criteriaBuilder.equal(root.get("user"), user));

    return session.createQuery(criteriaQuery).getResultList();
  }

  public CartItem findByUserAndProduct(User user, Product product) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<CartItem> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<CartItem> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(
        criteriaBuilder.and(criteriaBuilder.equal(root.get("user"), user),
            criteriaBuilder.equal(root.get("product"), product)));

    return session.createQuery(criteriaQuery).getSingleResult();
  }

}