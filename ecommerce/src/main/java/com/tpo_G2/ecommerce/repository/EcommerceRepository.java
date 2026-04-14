package com.tpo_G2.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo_G2.ecommerce.model.*;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
public interface CarritoRepository extends JpaRepository<Carrito, Long> {}
public interface PedidoRepository extends JpaRepository<Pedido, Long> {}
