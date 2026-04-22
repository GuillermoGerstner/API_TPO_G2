package com.tpo_G2.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.model.Carrito;
import com.tpo_G2.ecommerce.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{id}/agregar")
    public Carrito addCarrito(@PathVariable Long id, @RequestParam Long productoId, @RequestParam int cantidad) {
        return carritoService.addProducto(id, productoId, cantidad);
    }
}
