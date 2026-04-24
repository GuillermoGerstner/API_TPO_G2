package com.tpo_G2.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.ImagenProducto;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long> {
    // Busca dentro de 'producto' la variable 'id'
    List<ImagenProducto> findByProductoId(Long id);
}
