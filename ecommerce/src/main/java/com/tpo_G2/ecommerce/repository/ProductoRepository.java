package com.tpo_G2.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}