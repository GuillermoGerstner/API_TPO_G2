package com.tpo_G2.ecommerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.model.*;
import com.tpo_G2.ecommerce.repository.*;

@Service
public class CarritoService {

  @Autowired
  private CarritoRepository carritoRepository;
  @Autowired
  private ProductoRepository productoRepository;

  public Carrito addProducto(Long carritoId, Long productoId, int cantidad){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    if(cantidad <= 0){
      throw new RuntimeException("Cantidad debe ser mayor a 0");
    }
    
    if(producto.getStock() < cantidad){
      throw new RuntimeException("Sin stock");
    }

    if(carrito.getItems() == null){
      carrito.setItems(new ArrayList<>());
    }

    Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
      .filter(item -> item.getProducto().getId().equals(productoId))
      .findFirst();

    if (itemExistente.isPresent()) {
      ItemCarrito item = itemExistente.get();
      int nuevaCantidad = item.getCantidad() + cantidad;
      if (nuevaCantidad > producto.getStock()) {
        throw new RuntimeException("Sin stock suficiente");
      }
      item.setCantidad(nuevaCantidad);
    } else {
         ItemCarrito item = new ItemCarrito();
          item.setProducto(producto);
          item.setCantidad(cantidad);
          item.setPrecioUnitario(producto.getPrecio());
          item.setCarrito(carrito);

          carrito.getItems().add(item);
    }
    return carritoRepository.save(carrito);
  }
  
  public Carrito getCarritoById(Long carritoId) {
    return carritoRepository.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
  }
}
