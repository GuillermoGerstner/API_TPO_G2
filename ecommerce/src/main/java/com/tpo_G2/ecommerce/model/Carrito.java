package com.tpo_G2.ecommerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "carrito")
public class Carrito{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_carrito")
  private Long id;

  // Relación 1:1 con Usuario
  @OneToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  @JsonIgnore // Evita que el carrito intente mostrar al usuario de nuevo
  private Usuario usuario;
  
  // Relación 1:N con los ítems del carrito
  // CascadeType.ALL para que al limpiar/borrar el carrito se gestionen sus ítems
  // orphanRemoval = true es útil para "limpiar" el carrito quitando elementos de la lista
  @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemCarrito> items;
}
