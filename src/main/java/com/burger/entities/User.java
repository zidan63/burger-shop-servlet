package com.burger.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Basic
  @Column(nullable = false, length = 100)
  private String fullName;

  @Basic
  @Column(nullable = false, length = 30)
  private String username;

  @Basic
  @Column(nullable = false, length = 30)
  private String password;

  @Basic
  @Column(nullable = false, length = 100)
  private String email;

}
