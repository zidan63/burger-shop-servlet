package com.burger.entities;

import org.hibernate.annotations.Where;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Address")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
public class Address extends BaseEntity {
  @Basic
  @Column(name = "Name", nullable = false)
  private String name;
  @Basic
  @Column(name = "Phone", nullable = false)
  private String phone;
  @Basic
  @Column(name = "FullName", nullable = false)
  private String fullName;
}
