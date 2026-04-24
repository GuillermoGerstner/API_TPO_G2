package com.tpo_G2.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ItemPedido{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_item")
  private Long id;

  @Column(nullable = false)
  private int cantidad;

  @Column(name = "precio_unitario", nullable = false)
  private double precioUnitario;

  @ManyToOne
  @JoinColumn(name = "id_pedido", nullable = false)
  @JsonIgnore // Solo queremos ver el producto y cantidad, no volver a ver todo el carrito/pedido
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "id_producto", nullable = false)
  private Producto producto;
}
