package com.burger.repositories;

import org.hibernate.Session;

import com.burger.entities.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<User> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

    return session.createQuery(criteriaQuery).uniqueResult();
  }

  public User findByUserNameOrEmail(String username, String email) {
    Session session = factory.getCurrentSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<User> root = criteriaQuery.from(type);
    criteriaQuery.select(root);

    criteriaQuery.where(
        criteriaBuilder.or(
            criteriaBuilder.equal(root.get("username"), username),
            criteriaBuilder.equal(root.get("email"), email)));

    return session.createQuery(criteriaQuery).uniqueResult();
  }

}
