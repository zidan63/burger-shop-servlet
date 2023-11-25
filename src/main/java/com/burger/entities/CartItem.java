package com.burger.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "CartItem")
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
  @JsonManagedReference
  User user;

  @ManyToOne
  @JoinColumn(name = "ProductId")
  @JsonManagedReference
  Product product;
}
