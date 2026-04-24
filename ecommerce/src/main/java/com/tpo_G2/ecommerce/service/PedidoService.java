package com.tpo_G2.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.CategoriaDTO;
import com.tpo_G2.ecommerce.dto.ItemPedidoDTO;
import com.tpo_G2.ecommerce.dto.PedidoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.dto.UsuarioDTO;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Pedido;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::toPedidoDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        return toPedidoDTO(pedido);
    }

    private PedidoDTO toPedidoDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getTotal(),
                toUsuarioDTO(pedido.getUsuario()),
                pedido.getItems().stream()
                        .map(item -> new ItemPedidoDTO(
                                item.getId(),
                                item.getCantidad(),
                                item.getPrecioUnitario(),
                                toProductoDTO(item.getProducto())
                        ))
                        .collect(Collectors.toList())
        );
    }

    private ProductoDTO toProductoDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria() != null
                        ? new CategoriaDTO(producto.getCategoria().getId(), producto.getCategoria().getNombre())
                        : null
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
}
