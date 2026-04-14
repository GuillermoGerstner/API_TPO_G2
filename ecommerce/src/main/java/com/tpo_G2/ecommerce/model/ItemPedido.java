package com.tpo_G2.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "itemPedido")
public class ItemPedido{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int cantidad;
  private double precioUnitario;

  @ManyToOne
  private Producto producto;

  @ManyToOne
  private Pedido pedido;
  
}
