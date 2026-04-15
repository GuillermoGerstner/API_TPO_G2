package com.tpo_G2.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
