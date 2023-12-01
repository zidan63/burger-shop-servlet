package com.burger.entities;

import java.util.HashSet;
import java.util.Set;

import com.burger.entities.serializer.ProductSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Where;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Product")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "user", "category", "supplier", "toppings" })
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Where(clause = "DeletedAt IS NULL")
// @JsonSerialize(using = ProductSerializer.class)
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "id")
public class Product extends BaseEntity {
  @Basic
  @Column(name = "Name", nullable = false)
  String name;

  @Basic
  @Column(name = "PriceRecipt", nullable = false)
  Float priceRecipt;

  @Basic
  @Column(name = "PriceSale", nullable = false)
  Float priceSale;

  @Basic
  @Column(name = "Stock", nullable = false)
  Integer stock;

  @Basic
  @Column(name = "Description")
  String description;

  @Basic
  @Column(name = "ImageName")
  String imageName;

  @ManyToOne
  @JoinColumn(name = "UserId")
  @JsonManagedReference
  private User user;

  @ManyToOne
  @JoinColumn(name = "CategoryId")
  @JsonManagedReference
  private Category category;

  @ManyToOne
  @JoinColumn(name = "SupplierId")
  @JsonManagedReference
  private Supplier supplier;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "ToppingDetail", joinColumns = {
      @JoinColumn(name = "ProductId")
  }, inverseJoinColumns = {
      @JoinColumn(name = "ToppingId") })
  @Builder.Default
  @JsonManagedReference
  private Set<Topping> toppings = new HashSet<>();

}
