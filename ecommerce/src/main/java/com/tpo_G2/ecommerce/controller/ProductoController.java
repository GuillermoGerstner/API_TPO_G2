package com.tpo_G2.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.dto.CreateProductoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/mis-productos")
    public List<ProductoDTO> getMisProductos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName(); 
        return productoService.getProductosByUsuarioEmail(emailUsuario);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> addProducto(@Valid @RequestBody CreateProductoDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName(); 
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.addProductoConEmail(request, emailUsuario));
    }

    @DeleteMapping("/{id}")
    public ProductoDTO deleteProductoById(@PathVariable Long id) {
        return productoService.deleteProductoById(id);
    }

    @PutMapping("/{id}")
    public ProductoDTO updateProducto(@PathVariable Long id, @Valid @RequestBody CreateProductoDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName();
        return productoService.updateProductoConEmail(id, request, emailUsuario);
    }
}
