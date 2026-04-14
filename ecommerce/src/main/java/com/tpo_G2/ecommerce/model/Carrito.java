package com.tpo_G2.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "carrito")
public class Carrito{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Usuario usuario;

  @OneToMany(mappedBy = "carrito")
  private List<ItemCarrito> items;
  
}
