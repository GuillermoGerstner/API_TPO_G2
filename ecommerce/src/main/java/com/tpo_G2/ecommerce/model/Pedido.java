package com.tpo_G2.ecommerce.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "pedido")
public class Pedido{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pedido")
  private Long id;

  @Column(nullable = false)
  private Date fecha;

  private String estado;

  @Column(nullable = false)
  private double total;
  
  // Snapshot de la dirección al momento de comprar
  @Column(name = "direccion_envio_calle")
  private String calle;

  @Column(name = "direccion_envio_ciudad")
  private String ciudad;

  @Column(name = "direccion_envio_cp")
  private String cp;

  @Column(name = "direccion_envio_pais")
  private String pais;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
  private List<ItemPedido> items;
}
