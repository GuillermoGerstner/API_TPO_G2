package com.tpo_G2.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.exception.BadRequestException;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    public Producto addProducto(Producto producto) {
        if(producto.getPrecio() < 0){
            throw new BadRequestException("El precio no puede ser negativo");
        }

        if(producto.getStock() < 0){
            throw new BadRequestException("El stock no puede ser negativo");
        }

        return productoRepository.save(producto);
    }

    public Producto deleteProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        productoRepository.deleteById(id);
        return producto;
    }

    public Producto updateProducto(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        if(productoActualizado.getPrecio() < 0){
            throw new BadRequestException("El precio no puede ser negativo");
        }
        if(productoActualizado.getStock() < 0){
            throw new BadRequestException("El stock no puede ser negativo");
        }

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setUsuario(productoActualizado.getUsuario());

        return productoRepository.save(producto);
    }
}