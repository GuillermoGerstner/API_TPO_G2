package com.tpo_G2.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ItemCarrito{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int cantidad;
  private double precioUnitario;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @ManyToOne
  @JoinColumn(name = "carrito_id")
  private Carrito carrito;
  
}
