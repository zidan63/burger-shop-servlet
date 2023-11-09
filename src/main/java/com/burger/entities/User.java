package com.burger.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

  @Basic
  @Column(name = "FullName", nullable = false, length = 100)
  private String fullName;

  @Basic
  @Column(name = "UserName", nullable = false, length = 30)
  private String username;

  @Basic
  @Column(name = "Password", nullable = false)
  private String password;

  @Basic
  @Column(name = "Email", nullable = false, length = 100)
  private String email;

}
