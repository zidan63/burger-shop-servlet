package com.burger.entities;

import org.hibernate.annotations.Where;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "User")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "role" })
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Where(clause = "DeletedAt IS NULL")
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

  @Basic
  @Column(name = "OTP")
  private String OTP;

  @ManyToOne
  @JoinColumn(name = "RoleId")
  private Role role;

}
