package com.tpo_G2.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "imagenes_producto")
public class ImagenProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @Column(name = "url_foto", nullable = false)
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Producto producto;
}
