package com.burger.entities;

import com.burger.entities.serializer.BillDetailSerializer;
import com.burger.entities.serializer.BillSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "BillDetail")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "product", "bill" })
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
@DynamicUpdate
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BillDetail extends BaseEntity {

  @Basic
  @Column(name = "Amount", nullable = false)
  Integer amount;

  @Basic
  @Column(name = "PriceSaleBill", nullable = false)
  Float priceSaleBill;

  @ManyToOne
  @JoinColumn(name = "ProductId")
  @JsonManagedReference
  Product product;

  @ManyToOne
  @JoinColumn(name = "BillId")
//  @JsonBackReference
  @JsonBackReference
  Bill bill;


}
