package com.burger.entities;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import com.burger.enums.BillStatus;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bill")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "DeletedAt IS NULL")
public class Bill extends BaseEntity {

  @Basic
  @Column(name = "Status", nullable = false)
  @Enumerated(EnumType.STRING)
  private BillStatus status;

  @ManyToOne
  @JoinColumn(name = "UserId")
  User user;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<BillDetail> billDetails = new HashSet<>();

}
