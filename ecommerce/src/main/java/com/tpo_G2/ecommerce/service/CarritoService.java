package com.tpo_G2.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.CarritoDTO;
import com.tpo_G2.ecommerce.dto.CategoriaDTO;
import com.tpo_G2.ecommerce.dto.ItemCarritoDTO;
import com.tpo_G2.ecommerce.dto.ItemPedidoDTO;
import com.tpo_G2.ecommerce.dto.PedidoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.dto.UsuarioDTO;
import com.tpo_G2.ecommerce.exception.BadRequestException;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Carrito;
import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.model.ItemCarrito;
import com.tpo_G2.ecommerce.model.ItemPedido;
import com.tpo_G2.ecommerce.model.Pedido;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.CarritoRepository;
import com.tpo_G2.ecommerce.repository.PedidoRepository;
import com.tpo_G2.ecommerce.repository.ProductoRepository;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;

@Service
public class CarritoService {

  @Autowired
  private CarritoRepository carritoRepository;
  @Autowired
  private ProductoRepository productoRepository;
  @Autowired
  private PedidoRepository pedidoRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  public CarritoDTO createCarrito(Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

    Carrito carrito = new Carrito();
    carrito.setUsuario(usuario);
    carrito.setItems(new ArrayList<>());

    return toCarritoDTO(carritoRepository.save(carrito));
}

  public CarritoDTO addProducto(Long carritoId, Long productoId, int cantidad){
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
    Carrito carritoGuardado = carritoRepository.save(carrito);
    return toCarritoDTO(carritoGuardado);
  }
  
  public CarritoDTO getCarritoById(Long carritoId) {
    Carrito carrito = carritoRepository.findById(carritoId)
            .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

    return toCarritoDTO(carrito);
  }

  public CarritoDTO deleteItem(Long carritoId, Long itemId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

    if(carrito.getItems() == null || carrito.getItems().isEmpty()){
      throw new BadRequestException("El carrito está vacío");
    }

    boolean eliminado = carrito.getItems().removeIf(item -> item.getId().equals(itemId));

    if(!eliminado){
      throw new ResourceNotFoundException("Item no encontrado en el carrito");
    }
    return toCarritoDTO(carritoRepository.save(carrito));
  }

  public CarritoDTO emptyCarrito(Long carritoId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    
    if(carrito.getItems() != null){
      carrito.getItems().clear();
    }
    return toCarritoDTO(carritoRepository.save(carrito));
  }

  public PedidoDTO checkout(Long carritoId){
    Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
    
    if(carrito.getItems() == null || carrito.getItems().isEmpty()){
      throw new BadRequestException("El carrito está vacío");
    }

    Pedido pedido = new Pedido();
    pedido.setFecha(new Date());
    pedido.setEstado("CONFIRMADO");
    pedido.setUsuario(carrito.getUsuario());
    pedido.setItems(new ArrayList<>());

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
    return toPedidoDTO(pedidoGuardado);
  }

  private CarritoDTO toCarritoDTO(Carrito carrito) {
    List<ItemCarritoDTO> itemsDTO = carrito.getItems().stream()
            .map(this::toItemCarritoDTO)
            .collect(Collectors.toList());

    return new CarritoDTO(
            carrito.getId(),
            toUsuarioDTO(carrito.getUsuario()),
            itemsDTO
    );
  }

  private ItemCarritoDTO toItemCarritoDTO(ItemCarrito item) {
    return new ItemCarritoDTO(
            item.getId(),
            item.getCantidad(),
            item.getPrecioUnitario(),
            toProductoDTO(item.getProducto())
    );
  }

  private ProductoDTO toProductoDTO(Producto producto) {
    return new ProductoDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getStock(),
            producto.getCategoria() != null ? toCategoriaDTO(producto.getCategoria()) : null
    );
  }

  private CategoriaDTO toCategoriaDTO(Categoria categoria) {
    return new CategoriaDTO(
            categoria.getId(),
            categoria.getNombre()
    );
  }

  private UsuarioDTO toUsuarioDTO(Usuario usuario) {
    if (usuario == null) {
        return null;
    }

    return new UsuarioDTO(
            usuario.getIdUsuario(),
            usuario.getUsername(),
            usuario.getEmail(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getRole()
    );
  }

  private PedidoDTO toPedidoDTO(Pedido pedido) {
      return new PedidoDTO(
              pedido.getId(),
              pedido.getFecha(),
              pedido.getEstado(),
              pedido.getTotal(),
              toUsuarioDTO(pedido.getUsuario()),
              pedido.getItems().stream()
                      .map(this::toItemPedidoDTO)
                      .collect(Collectors.toList())
      );
  }

  private ItemPedidoDTO toItemPedidoDTO(ItemPedido item) {
      return new ItemPedidoDTO(
              item.getId(),
              item.getCantidad(),
              item.getPrecioUnitario(),
              toProductoDTO(item.getProducto())
      );
  }
}
