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
@Table(name = "Category")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
public class Category extends BaseEntity {

  @Basic
  @Column(name = "Code", nullable = false)
  String code;

  @Basic
  @Column(name = "Name", nullable = false)
  String name;
}
