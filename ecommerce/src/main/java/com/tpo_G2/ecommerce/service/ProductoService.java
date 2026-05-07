package com.tpo_G2.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.CategoriaDTO;
import com.tpo_G2.ecommerce.dto.CreateProductoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.exception.BadRequestException;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;
import com.tpo_G2.ecommerce.repository.ProductoRepository;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
            .map(producto -> new ProductoDTO(
                producto.getId(), 
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getPrecio(), 
                producto.getStock(), 
                producto.getCategoria() != null ? new CategoriaDTO(producto.getCategoria().getId(), producto.getCategoria().getNombre()) : null
            ))
            .collect(Collectors.toList());
    }

    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        return toProductoDTO(producto);
    }

    public ProductoDTO addProducto(CreateProductoDTO request) {
        if (request.getPrecio() < 0) {
            throw new BadRequestException("El precio no puede ser negativo");
        }

        if (request.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser negativo");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));

        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getIdCategoria()));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);

        return toProductoDTO(productoRepository.save(producto));
    }

    public ProductoDTO deleteProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        productoRepository.deleteById(id);
        return toProductoDTO(producto);
    }

    public ProductoDTO updateProducto(Long id, CreateProductoDTO request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        if (request.getPrecio() < 0) {
            throw new BadRequestException("El precio no puede ser negativo");
        }

        if (request.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser negativo");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));

        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getIdCategoria()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);

        return toProductoDTO(productoRepository.save(producto));
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
}