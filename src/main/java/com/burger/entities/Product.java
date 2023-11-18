package com.burger.entities;

import java.util.HashSet;
import java.util.Set;

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
@EqualsAndHashCode(callSuper = false, exclude = { "user", "category", "suplier", "colors" })
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Where(clause = "DeletedAt IS NULL")
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
  private User user;

  @ManyToOne
  @JoinColumn(name = "CategoryId")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "SuplierId")
  private Suplier suplier;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "ColorDetail", joinColumns = {
      @JoinColumn(name = "ProductId")
  }, inverseJoinColumns = {
      @JoinColumn(name = "ColorId") })
  @Builder.Default
  private Set<Color> colors = new HashSet<>();

}
