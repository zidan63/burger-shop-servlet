package com.burger.config;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.burger.entities.*;

public class HibernateInitialize {
  private static SessionFactory sessionFactory;

  private static final List<Class<?>> classes = List.of(User.class, Role.class, Permission.class, Category.class,
      Supplier.class, Topping.class, Product.class, Bill.class, BillDetail.class, CartItem.class, Address.class);

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration();

        classes.forEach(configuration::addAnnotatedClass);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return sessionFactory;
  }
}