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

@Entity
@Table(name = "Category")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
public class CartItem extends BaseEntity {

  @Basic
  @Column(name = "Amount", nullable = false)
  Integer amount;

  @ManyToOne
  @JoinColumn(name = "UserId")
  User user;

  @ManyToOne
  @JoinColumn(name = "ProductId")
  Product product;
}
