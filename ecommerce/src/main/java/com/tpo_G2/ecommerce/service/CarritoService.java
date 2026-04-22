package com.tpo_G2.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.model.*;
import com.tpo_G2.ecommerce.repository.*;

@Service
public class CarritoService {

  @Autowired
  private CarritoRepository carritoRepository;
  private ProductoRepository productoRepository;

  public Carrito addProducto(Long carritoId, Long productoId, int cantidad){
    Carrito carrito = carritoRepository.findById(carritoId).orElse(null);
    Producto producto = productoRepository.findById(productoId).orElse(null);

    if(producto.getStock() < cantidad){
      throw new RuntimeException("Sin stock");
    }

    ItemCarrito item = new ItemCarrito();
    item.setProducto(producto);
    item.setCantidad(cantidad);
    item.setPrecioUnitario(producto.getPrecio());
    item.setCarrito(carrito);

    carrito.getItems().add(item);

    return carritoRepository.save(carrito);
  }
}
