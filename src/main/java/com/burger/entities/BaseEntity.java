package com.burger.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  @Builder.Default
  protected Integer id = null;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CreatedAt")
  protected Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UpdatedAt")
  protected Date updatedAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "DeletedAt")
  protected Date deletedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }

}