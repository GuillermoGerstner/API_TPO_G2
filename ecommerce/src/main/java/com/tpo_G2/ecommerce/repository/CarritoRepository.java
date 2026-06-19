package com.tpo_G2.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario_Email(String email);
}