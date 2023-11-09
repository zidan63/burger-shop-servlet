package com.burger.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  protected Integer id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CreatedAt")
  protected Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UpdatedAt")
  protected Date updatedAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "DeletedAt")
  protected Date deletedAt;

}