package com.tpo_G2.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ItemPedido{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int cantidad;
  private double precioUnitario;

  @ManyToOne
  private Producto producto;

  @ManyToOne
  @JsonIgnore
  private Pedido pedido;
  
}
