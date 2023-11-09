package com.burger.repositories;

import java.util.List;

import org.hibernate.Session;

import com.burger.entities.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserRepository extends BaseRepository<User> {
  private static UserRepository instance;

  private UserRepository() {
    super(User.class);
  }

  public static UserRepository getInstance() {
    if (instance == null)
      instance = new UserRepository();
    return instance;
  }

  public User findByUserName(String username) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = getCriteriaQuery(session);
    Root<User> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    Predicate equal = criteriaBuilder.equal(root.get("username"), username);
    criteriaQuery.where(equal);

    List<User> users = session.createQuery(criteriaQuery).getResultList();

    if (users.size() != 0) {
      return users.get(0);
    }

    return null;
  }

}
