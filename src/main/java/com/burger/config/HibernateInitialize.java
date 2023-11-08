package com.burger.config;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.burger.entities.*;

public class HibernateInitialize {
  private static SessionFactory sessionFactory;

  private static final List<Class<?>> classes = List.of(User.class);

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration();
        configuration.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
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