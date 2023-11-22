package com.burger.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Bill")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "billDetails", "user" })
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Where(clause = "DeletedAt IS NULL")
public class Bill extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "UserId")
  private User user;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "AddressId", referencedColumnName = "Id")
  private Address address;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @Builder.Default
  private Set<BillDetail> billDetails = new HashSet<>();

  @Basic
  @Column(name = "Status", nullable = false)
  @ColumnDefault(value="0")
  @Enumerated(EnumType.ORDINAL)
  Status status;

}
