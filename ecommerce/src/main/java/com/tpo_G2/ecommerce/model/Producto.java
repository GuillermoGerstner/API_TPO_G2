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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;
    
    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Float precio;

    @Column(nullable = false)
    private Integer stock;

    // El usuario que creó el producto
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // La categoría a la que pertenece
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    // Relación 1:N con sus imágenes
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<ImagenProducto> imagenes;

    // Relaciones con los ítems (Carrito y Pedido)
    // Se usa mappedBy porque el ID del producto está físicamente en las tablas de ítems
    @OneToMany(mappedBy = "producto")
    @JsonIgnore // Evita que al ver un producto se listen todos los carritos donde aparece
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemCarrito> itemsCarrito;

    @OneToMany(mappedBy = "producto")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemPedido> itemsPedido;
    
}
