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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class ItemCarrito{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_item")
  private Long id;

  @Column(nullable = false)
  private int cantidad;

  private double precioUnitario;

  // Relación con el Carrito al que pertenece
  @ManyToOne
  @JoinColumn(name = "id_carrito", nullable = false)
  @JsonIgnore // Solo queremos ver el producto y cantidad, no volver a ver todo el carrito/pedido
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Carrito carrito;

  // Relación con el Producto que se quiere comprar
  @ManyToOne
  @JoinColumn(name = "id_producto", nullable = false)
  private Producto producto;
}
