package com.tpo_G2.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.exception.BadRequestException;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.*;
import com.tpo_G2.ecommerce.repository.*;

@Service
public class CarritoService {

  @Autowired
  private CarritoRepository carritoRepository;
  @Autowired
  private ProductoRepository productoRepository;
  @Autowired
  private PedidoRepository pedidoRepository;

  public Carrito addProducto(Long carritoId, Long productoId, int cantidad){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

    if(cantidad <= 0){
      throw new BadRequestException("Cantidad debe ser mayor a 0");
    }
    
    if(producto.getStock() < cantidad){
      throw new BadRequestException("Sin stock");
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
        throw new BadRequestException("Sin stock suficiente");
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
    return carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
  }

  public Carrito deleteItem(Long carritoId, Long itemId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

    if(carrito.getItems() == null || carrito.getItems().isEmpty()){
      throw new BadRequestException("El carrito está vacío");
    }

    boolean eliminado = carrito.getItems().removeIf(item -> item.getId().equals(itemId));

    if(!eliminado){
      throw new ResourceNotFoundException("Item no encontrado en el carrito");
    }
    return carritoRepository.save(carrito);
  }

  public Carrito emptyCarrito(Long carritoId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    
    if(carrito.getItems() != null){
      carrito.getItems().clear();
    }
    return carritoRepository.save(carrito);
  }

  public Pedido checkout(Long carritoId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    
    if(carrito.getItems() == null || carrito.getItems().isEmpty()){
      throw new BadRequestException("El carrito está vacío");
    }

    Pedido pedido = new Pedido();
    pedido.setFecha(new Date());
    pedido.setEstado("CONFIRMADO");
    pedido.setUsuario(carrito.getUsuario());

    double total = 0;
    for(ItemCarrito itemCarrito : carrito.getItems()){
      Producto producto = itemCarrito.getProducto();
      if(producto.getStock() < itemCarrito.getCantidad()){
        throw new BadRequestException("Sin stock suficiente para el producto: " + producto.getNombre());
      }

      producto.setStock(producto.getStock() - itemCarrito.getCantidad());
      productoRepository.save(producto);

      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setProducto(producto);
      itemPedido.setCantidad(itemCarrito.getCantidad());
      itemPedido.setPrecioUnitario(producto.getPrecio());
      itemPedido.setPedido(pedido);

      pedido.getItems().add(itemPedido);
      total += itemCarrito.getCantidad() * producto.getPrecio();
    }
    pedido.setTotal(total);
    Pedido pedidoGuardado = pedidoRepository.save(pedido);
    carrito.getItems().clear();
    carritoRepository.save(carrito);
    return pedidoGuardado;
  }
}
