package com.tpo_G2.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoService.getAllProductos();
    }
    
    @GetMapping("/{id}")
    public ProductoDTO getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    public Producto addProducto(@RequestBody Producto producto) {
        return productoService.addProducto(producto);
    }

    @DeleteMapping("/{id}")
    public Producto deleteProductoById(@PathVariable Long id) {
        return productoService.deleteProductoById(id);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.updateProducto(id, producto);
    }
}
