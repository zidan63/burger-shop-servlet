package com.burger.entities;

import com.burger.entities.serializer.BillDetailSerializer;
import com.burger.entities.serializer.UserSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
@Where(clause = "DeletedAt IS NULL")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
  @JsonManagedReference
  private Role role;

}
