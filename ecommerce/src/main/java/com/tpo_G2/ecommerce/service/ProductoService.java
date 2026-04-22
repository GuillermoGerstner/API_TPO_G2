package com.tpo_G2.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return productoRepository.findById(id).orElse(null);
    }

    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto deleteProductoById(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            productoRepository.deleteById(id);
        }

        return producto;
    }

    public Producto updateProducto(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null) {
            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setCategoria(productoActualizado.getCategoria());

            return productoRepository.save(producto);
        }
        return null;
    }
}