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
@Table(name = "BillDetail")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "product", "bill" })
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
public class BillDetail extends BaseEntity {

  @Basic
  @Column(name = "Amount", nullable = false)
  Integer amount;

  @Basic
  @Column(name = "PriceSaleBill", nullable = false)
  Float priceSaleBill;

  @ManyToOne
  @JoinColumn(name = "ProductId")
  Product product;

  @ManyToOne
  @JoinColumn(name = "BillId")
  Bill bill;
}
