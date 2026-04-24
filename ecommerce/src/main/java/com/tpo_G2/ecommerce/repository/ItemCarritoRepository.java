package com.tpo_G2.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.ItemCarrito;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    
}