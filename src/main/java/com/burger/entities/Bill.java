package com.burger.entities;

import java.util.HashSet;
import java.util.Set;

import com.burger.entities.serializer.BillDetailSerializer;
import com.burger.entities.serializer.BillSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity()
@DynamicUpdate
@Table(name = "Bill")
@Data
@EqualsAndHashCode(callSuper = false, exclude = { "billDetails", "user" })
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Where(clause = "DeletedAt IS NULL")
//@JsonSerialize(using = BillSerializer.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Bill extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "UserId")
  @JsonManagedReference
  private User user;

  @OneToOne(cascade = CascadeType.ALL)
//  @JsonBackReference
  @JoinColumn(name = "AddressId", referencedColumnName = "Id")
  @JsonManagedReference
  private Address address;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @Builder.Default
  @JsonManagedReference

  private Set<BillDetail> billDetails = new HashSet<>();

  @Basic
  @Column(name = "Status", nullable = false)
  @ColumnDefault(value="0")
  @Enumerated(EnumType.ORDINAL)
  @JsonManagedReference
  Status status;


  @ManyToOne
  @JoinColumn(name = "EmployeeId")
  @JsonManagedReference
  User employee;

}
